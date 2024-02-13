import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMaterial } from 'app/entities/material/material.model';
import { MaterialService } from 'app/entities/material/service/material.service';
import { IProcesVerbal } from 'app/entities/proces-verbal/proces-verbal.model';
import { ProcesVerbalService } from 'app/entities/proces-verbal/service/proces-verbal.service';
import { Procedura } from 'app/entities/enumerations/procedura.model';
import { JTMaterialProcesVerbalService } from '../service/jt-material-proces-verbal.service';
import { IJTMaterialProcesVerbal } from '../jt-material-proces-verbal.model';
import { JTMaterialProcesVerbalFormService, JTMaterialProcesVerbalFormGroup } from './jt-material-proces-verbal-form.service';

@Component({
  standalone: true,
  selector: 'jhi-jt-material-proces-verbal-update',
  templateUrl: './jt-material-proces-verbal-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class JTMaterialProcesVerbalUpdateComponent implements OnInit {
  isSaving = false;
  jTMaterialProcesVerbal: IJTMaterialProcesVerbal | null = null;
  proceduraValues = Object.keys(Procedura);

  materialsSharedCollection: IMaterial[] = [];
  procesVerbalsSharedCollection: IProcesVerbal[] = [];

  editForm: JTMaterialProcesVerbalFormGroup = this.jTMaterialProcesVerbalFormService.createJTMaterialProcesVerbalFormGroup();

  constructor(
    protected jTMaterialProcesVerbalService: JTMaterialProcesVerbalService,
    protected jTMaterialProcesVerbalFormService: JTMaterialProcesVerbalFormService,
    protected materialService: MaterialService,
    protected procesVerbalService: ProcesVerbalService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareMaterial = (o1: IMaterial | null, o2: IMaterial | null): boolean => this.materialService.compareMaterial(o1, o2);

  compareProcesVerbal = (o1: IProcesVerbal | null, o2: IProcesVerbal | null): boolean =>
    this.procesVerbalService.compareProcesVerbal(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jTMaterialProcesVerbal }) => {
      this.jTMaterialProcesVerbal = jTMaterialProcesVerbal;
      if (jTMaterialProcesVerbal) {
        this.updateForm(jTMaterialProcesVerbal);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jTMaterialProcesVerbal = this.jTMaterialProcesVerbalFormService.getJTMaterialProcesVerbal(this.editForm);
    if (jTMaterialProcesVerbal.id !== null) {
      this.subscribeToSaveResponse(this.jTMaterialProcesVerbalService.update(jTMaterialProcesVerbal));
    } else {
      this.subscribeToSaveResponse(this.jTMaterialProcesVerbalService.create(jTMaterialProcesVerbal));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJTMaterialProcesVerbal>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(jTMaterialProcesVerbal: IJTMaterialProcesVerbal): void {
    this.jTMaterialProcesVerbal = jTMaterialProcesVerbal;
    this.jTMaterialProcesVerbalFormService.resetForm(this.editForm, jTMaterialProcesVerbal);

    this.materialsSharedCollection = this.materialService.addMaterialToCollectionIfMissing<IMaterial>(
      this.materialsSharedCollection,
      jTMaterialProcesVerbal.produs,
    );
    this.procesVerbalsSharedCollection = this.procesVerbalService.addProcesVerbalToCollectionIfMissing<IProcesVerbal>(
      this.procesVerbalsSharedCollection,
      jTMaterialProcesVerbal.procesVerbal,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.materialService
      .query()
      .pipe(map((res: HttpResponse<IMaterial[]>) => res.body ?? []))
      .pipe(
        map((materials: IMaterial[]) =>
          this.materialService.addMaterialToCollectionIfMissing<IMaterial>(materials, this.jTMaterialProcesVerbal?.produs),
        ),
      )
      .subscribe((materials: IMaterial[]) => (this.materialsSharedCollection = materials));

    this.procesVerbalService
      .query()
      .pipe(map((res: HttpResponse<IProcesVerbal[]>) => res.body ?? []))
      .pipe(
        map((procesVerbals: IProcesVerbal[]) =>
          this.procesVerbalService.addProcesVerbalToCollectionIfMissing<IProcesVerbal>(
            procesVerbals,
            this.jTMaterialProcesVerbal?.procesVerbal,
          ),
        ),
      )
      .subscribe((procesVerbals: IProcesVerbal[]) => (this.procesVerbalsSharedCollection = procesVerbals));
  }
}

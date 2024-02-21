import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICompanie } from 'app/entities/companie/companie.model';
import { CompanieService } from 'app/entities/companie/service/companie.service';
import { Procedura } from 'app/entities/enumerations/procedura.model';
import { MaterialService } from '../service/material.service';
import { IMaterial } from '../material.model';
import { MaterialFormService, MaterialFormGroup } from './material-form.service';

@Component({
  standalone: true,
  selector: 'jhi-material-update',
  templateUrl: './material-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MaterialUpdateComponent implements OnInit {
  isSaving = false;
  material: IMaterial | null = null;
  proceduraValues = Object.keys(Procedura);

  companiesSharedCollection: ICompanie[] = [];

  editForm: MaterialFormGroup = this.materialFormService.createMaterialFormGroup();

  constructor(
    protected materialService: MaterialService,
    protected materialFormService: MaterialFormService,
    protected companieService: CompanieService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCompanie = (o1: ICompanie | null, o2: ICompanie | null): boolean => this.companieService.compareCompanie(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ material }) => {
      this.material = material;
      if (material) {
        this.updateForm(material);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const material = this.materialFormService.getMaterial(this.editForm);
    if (material.id !== null) {
      this.subscribeToSaveResponse(this.materialService.update(material));
    } else {
      this.subscribeToSaveResponse(this.materialService.create(material));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMaterial>>): void {
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

  protected updateForm(material: IMaterial): void {
    this.material = material;
    this.materialFormService.resetForm(this.editForm, material);

    this.companiesSharedCollection = this.companieService.addCompanieToCollectionIfMissing<ICompanie>(
      this.companiesSharedCollection,
      material.companie,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.companieService
      .query()
      .pipe(map((res: HttpResponse<ICompanie[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompanie[]) =>
          this.companieService.addCompanieToCollectionIfMissing<ICompanie>(companies, this.material?.companie),
        ),
      )
      .subscribe((companies: ICompanie[]) => (this.companiesSharedCollection = companies));
  }
}

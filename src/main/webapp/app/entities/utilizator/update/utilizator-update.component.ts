import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICompanie } from 'app/entities/companie/companie.model';
import { CompanieService } from 'app/entities/companie/service/companie.service';
import { Functie } from 'app/entities/enumerations/functie.model';
import { UtilizatorService } from '../service/utilizator.service';
import { IUtilizator } from '../utilizator.model';
import { UtilizatorFormService, UtilizatorFormGroup } from './utilizator-form.service';

@Component({
  standalone: true,
  selector: 'jhi-utilizator-update',
  templateUrl: './utilizator-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class UtilizatorUpdateComponent implements OnInit {
  isSaving = false;
  utilizator: IUtilizator | null = null;
  functieValues = Object.keys(Functie);

  companiesSharedCollection: ICompanie[] = [];

  editForm: UtilizatorFormGroup = this.utilizatorFormService.createUtilizatorFormGroup();

  constructor(
    protected utilizatorService: UtilizatorService,
    protected utilizatorFormService: UtilizatorFormService,
    protected companieService: CompanieService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCompanie = (o1: ICompanie | null, o2: ICompanie | null): boolean => this.companieService.compareCompanie(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ utilizator }) => {
      this.utilizator = utilizator;
      if (utilizator) {
        this.updateForm(utilizator);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const utilizator = this.utilizatorFormService.getUtilizator(this.editForm);
    if (utilizator.id !== null) {
      this.subscribeToSaveResponse(this.utilizatorService.update(utilizator));
    } else {
      this.subscribeToSaveResponse(this.utilizatorService.create(utilizator));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUtilizator>>): void {
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

  protected updateForm(utilizator: IUtilizator): void {
    this.utilizator = utilizator;
    this.utilizatorFormService.resetForm(this.editForm, utilizator);

    this.companiesSharedCollection = this.companieService.addCompanieToCollectionIfMissing<ICompanie>(
      this.companiesSharedCollection,
      utilizator.companie,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.companieService
      .query()
      .pipe(map((res: HttpResponse<ICompanie[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompanie[]) =>
          this.companieService.addCompanieToCollectionIfMissing<ICompanie>(companies, this.utilizator?.companie),
        ),
      )
      .subscribe((companies: ICompanie[]) => (this.companiesSharedCollection = companies));
  }
}
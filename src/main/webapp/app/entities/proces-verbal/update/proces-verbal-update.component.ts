import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICompanie } from 'app/entities/companie/companie.model';
import { CompanieService } from 'app/entities/companie/service/companie.service';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';
import { IUtilizator } from 'app/entities/utilizator/utilizator.model';
import { UtilizatorService } from 'app/entities/utilizator/service/utilizator.service';
import { ProcesVerbalService } from '../service/proces-verbal.service';
import { IProcesVerbal } from '../proces-verbal.model';
import { ProcesVerbalFormService, ProcesVerbalFormGroup } from './proces-verbal-form.service';

@Component({
  standalone: true,
  selector: 'jhi-proces-verbal-update',
  templateUrl: './proces-verbal-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProcesVerbalUpdateComponent implements OnInit {
  isSaving = false;
  procesVerbal: IProcesVerbal | null = null;

  companiesSharedCollection: ICompanie[] = [];
  clientsSharedCollection: IClient[] = [];
  utilizatorsSharedCollection: IUtilizator[] = [];

  editForm: ProcesVerbalFormGroup = this.procesVerbalFormService.createProcesVerbalFormGroup();

  constructor(
    protected procesVerbalService: ProcesVerbalService,
    protected procesVerbalFormService: ProcesVerbalFormService,
    protected companieService: CompanieService,
    protected clientService: ClientService,
    protected utilizatorService: UtilizatorService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCompanie = (o1: ICompanie | null, o2: ICompanie | null): boolean => this.companieService.compareCompanie(o1, o2);

  compareClient = (o1: IClient | null, o2: IClient | null): boolean => this.clientService.compareClient(o1, o2);

  compareUtilizator = (o1: IUtilizator | null, o2: IUtilizator | null): boolean => this.utilizatorService.compareUtilizator(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ procesVerbal }) => {
      this.procesVerbal = procesVerbal;
      if (procesVerbal) {
        this.updateForm(procesVerbal);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const procesVerbal = this.procesVerbalFormService.getProcesVerbal(this.editForm);
    if (procesVerbal.id !== null) {
      this.subscribeToSaveResponse(this.procesVerbalService.update(procesVerbal));
    } else {
      this.subscribeToSaveResponse(this.procesVerbalService.create(procesVerbal));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProcesVerbal>>): void {
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

  protected updateForm(procesVerbal: IProcesVerbal): void {
    this.procesVerbal = procesVerbal;
    this.procesVerbalFormService.resetForm(this.editForm, procesVerbal);

    this.companiesSharedCollection = this.companieService.addCompanieToCollectionIfMissing<ICompanie>(
      this.companiesSharedCollection,
      procesVerbal.companie,
    );
    this.clientsSharedCollection = this.clientService.addClientToCollectionIfMissing<IClient>(
      this.clientsSharedCollection,
      procesVerbal.client,
    );
    this.utilizatorsSharedCollection = this.utilizatorService.addUtilizatorToCollectionIfMissing<IUtilizator>(
      this.utilizatorsSharedCollection,
      procesVerbal.operator,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.companieService
      .query()
      .pipe(map((res: HttpResponse<ICompanie[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompanie[]) =>
          this.companieService.addCompanieToCollectionIfMissing<ICompanie>(companies, this.procesVerbal?.companie),
        ),
      )
      .subscribe((companies: ICompanie[]) => (this.companiesSharedCollection = companies));

    this.clientService
      .query()
      .pipe(map((res: HttpResponse<IClient[]>) => res.body ?? []))
      .pipe(map((clients: IClient[]) => this.clientService.addClientToCollectionIfMissing<IClient>(clients, this.procesVerbal?.client)))
      .subscribe((clients: IClient[]) => (this.clientsSharedCollection = clients));

    this.utilizatorService
      .query()
      .pipe(map((res: HttpResponse<IUtilizator[]>) => res.body ?? []))
      .pipe(
        map((utilizators: IUtilizator[]) =>
          this.utilizatorService.addUtilizatorToCollectionIfMissing<IUtilizator>(utilizators, this.procesVerbal?.operator),
        ),
      )
      .subscribe((utilizators: IUtilizator[]) => (this.utilizatorsSharedCollection = utilizators));
  }
}

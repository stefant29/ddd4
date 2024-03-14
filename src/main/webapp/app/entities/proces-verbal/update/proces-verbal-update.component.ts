import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICompanie } from 'app/entities/companie/companie.model';
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
  title = 'Creare Proces Verbal';

  companiesSharedCollection: ICompanie[] = [];
  clientsSharedCollection: IClient[] = [];
  utilizatorsSharedCollection: IUtilizator[] = [];

  editForm: ProcesVerbalFormGroup = this.procesVerbalFormService.createProcesVerbalFormGroup();

  constructor(
    protected procesVerbalService: ProcesVerbalService,
    protected procesVerbalFormService: ProcesVerbalFormService,
    protected clientService: ClientService,
    protected utilizatorService: UtilizatorService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareClient = (o1: IClient | null, o2: IClient | null): boolean => this.clientService.compareClient(o1, o2);

  compareUtilizator = (o1: IUtilizator | null, o2: IUtilizator | null): boolean => this.utilizatorService.compareUtilizator(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ procesVerbal }) => {
      this.procesVerbal = procesVerbal;
      if (procesVerbal) {
        this.title = 'Editare Proces Verbal';
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
    console.log('SAVING');
    console.log(procesVerbal);

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
  }

  protected loadRelationshipsOptions(): void {
    this.clientService.getIdDenumireList().subscribe({
      next: (res: HttpResponse<IClient[]>) => {
        this.clientsSharedCollection = res.body ?? [];
      },
    });

    this.utilizatorService.getIdNumePrenumeList().subscribe({
      next: (res: HttpResponse<IClient[]>) => {
        this.utilizatorsSharedCollection = res.body ?? [];
      },
    });
  }
}

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
import { TableModule } from 'primeng/table';
import { IJTMaterialProcesVerbal } from 'app/entities/jt-material-proces-verbal/jt-material-proces-verbal.model';
import { Procedura } from 'app/entities/enumerations/procedura.model';
import { ButtonModule } from 'primeng/button';
import { IMaterial } from 'app/entities/material/material.model';
import { DropdownModule } from 'primeng/dropdown';
import { InputNumberModule } from 'primeng/inputnumber';
import { MaterialService } from 'app/entities/material/service/material.service';

@Component({
  standalone: true,
  selector: 'jhi-proces-verbal-update',
  templateUrl: './proces-verbal-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, TableModule, ButtonModule, DropdownModule, InputNumberModule],
})
export class ProcesVerbalUpdateComponent implements OnInit {
  isSaving = false;
  procesVerbal: IProcesVerbal | null = null;
  title = 'Creare Proces Verbal';

  materialeDeratizari: Array<IMaterial> = [];
  jtMaterialeDeratizari: Array<IJTMaterialProcesVerbal> = [];

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
    protected materialService: MaterialService,
  ) {}

  compareClient = (o1: IClient | null, o2: IClient | null): boolean => this.clientService.compareClient(o1, o2);

  compareUtilizator = (o1: IUtilizator | null, o2: IUtilizator | null): boolean => this.utilizatorService.compareUtilizator(o1, o2);

  ngOnInit(): void {
    this.materialService.getIdDenumireList('DERATIZARE').subscribe({
      next: (res: HttpResponse<IMaterial[]>) => {
        this.materialeDeratizari = res.body ?? [];
      },
    });

    // this.jtMaterialeDeratizari = [
    //   {
    //     id: '1',
    //     procedura: Procedura.DERATIZARE,
    //     cantitate: 10,
    //     produs: this.materialeDeratizari[0],
    //     procesVerbal: null, // Replace null with actual process verbal object
    //   },
    //   {
    //     id: '2',
    //     procedura: Procedura.DERATIZARE,
    //     cantitate: 20,
    //     produs: this.materialeDeratizari[1],
    //     procesVerbal: null, // Replace null with actual process verbal object
    //   },
    // ];

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
      this.subscribeToSaveResponse(this.procesVerbalService.create(procesVerbal, this.jtMaterialeDeratizari));
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

  protected addDeratizare() {
    this.jtMaterialeDeratizari.push({
      id: '4',
      procedura: Procedura.DERATIZARE,
      cantitate: null,
      produs: null,
      procesVerbal: null, // Replace null with actual process verbal object
    });
  }

  protected deleteDeratizare(index: number) {
    alert(index);
    this.jtMaterialeDeratizari.splice(index, 1);
  }
}

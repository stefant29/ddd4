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
  mapMaterialeDeratizari: Map<string, IMaterial>;
  jtMaterialeDeratizari: Array<IJTMaterialProcesVerbal> = [];

  materialeDezinsectii: Array<IMaterial> = [];
  mapMaterialeDezinsectii: Map<string, IMaterial>;
  jtMaterialeDezinsectii: Array<IJTMaterialProcesVerbal> = [];

  materialeDezinfectii: Array<IMaterial> = [];
  mapMaterialeDezinfectii: Map<string, IMaterial>;
  jtMaterialeDezinfectii: Array<IJTMaterialProcesVerbal> = [];

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

        this.mapMaterialeDeratizari = new Map(this.materialeDeratizari.map(obj => [obj.id, obj]));

        if (!!this.procesVerbal && !!this.procesVerbal.jTMaterialProcesVerbals) {
          this.jtMaterialeDeratizari = this.procesVerbal?.jTMaterialProcesVerbals.filter(
            (item: IJTMaterialProcesVerbal) => item.procedura === 'DERATIZARE',
          );

          this.jtMaterialeDeratizari.map((item: IJTMaterialProcesVerbal) => {
            if (item.produs) {
              item.produs = this.mapMaterialeDeratizari.get(item.produs.id);
            }
          });
        }
      },
    });
    this.materialService.getIdDenumireList('DEZINSECTIE').subscribe({
      next: (res: HttpResponse<IMaterial[]>) => {
        this.materialeDezinsectii = res.body ?? [];

        this.mapMaterialeDezinsectii = new Map(this.materialeDezinsectii.map(obj => [obj.id, obj]));

        if (!!this.procesVerbal && !!this.procesVerbal.jTMaterialProcesVerbals) {
          this.jtMaterialeDezinsectii = this.procesVerbal?.jTMaterialProcesVerbals.filter(
            (item: IJTMaterialProcesVerbal) => item.procedura === 'DEZINSECTIE',
          );

          this.jtMaterialeDezinsectii.map((item: IJTMaterialProcesVerbal) => {
            if (item.produs) {
              item.produs = this.mapMaterialeDezinsectii.get(item.produs.id);
            }
          });
        }
      },
    });
    this.materialService.getIdDenumireList('DEZINFECTIE').subscribe({
      next: (res: HttpResponse<IMaterial[]>) => {
        this.materialeDezinfectii = res.body ?? [];

        this.mapMaterialeDezinfectii = new Map(this.materialeDezinfectii.map(obj => [obj.id, obj]));

        if (!!this.procesVerbal && !!this.procesVerbal.jTMaterialProcesVerbals) {
          this.jtMaterialeDezinfectii = this.procesVerbal?.jTMaterialProcesVerbals.filter(
            (item: IJTMaterialProcesVerbal) => item.procedura === 'DEZINFECTIE',
          );

          this.jtMaterialeDezinfectii.map((item: IJTMaterialProcesVerbal) => {
            if (item.produs) {
              item.produs = this.mapMaterialeDezinfectii.get(item.produs.id);
            }
          });
        }
      },
    });

    this.activatedRoute.data.subscribe(({ procesVerbal }) => {
      this.procesVerbal = procesVerbal;
      if (!!this.procesVerbal) {
        this.procesVerbal['jTMaterialProcesVerbals'] = procesVerbal.jtmaterialProcesVerbals;
        // if (!!this.procesVerbal.jTMaterialProcesVerbals) {
        //   this.jtMaterialeDezinsectii = this.procesVerbal.jTMaterialProcesVerbals.filter((item: IJTMaterialProcesVerbal) => item.procedura === 'DEZINSECTIE');
        //   this.jtMaterialeDezinfectii = this.procesVerbal.jTMaterialProcesVerbals.filter((item: IJTMaterialProcesVerbal) => item.procedura === 'DEZINFECTIE');
        // }
      }

      // console.log("pv: ", this.procesVerbal);
      // console.log(this.jtMaterialeDezinsectii);

      // if (!!this.procesVerbal && !!this.procesVerbal.jTMaterialProcesVerbals) {
      //   this.jtMaterialeDeratizari = this.procesVerbal?.jTMaterialProcesVerbals
      //     .filter((item: IJTMaterialProcesVerbal) => item.procedura === 'DERATIZARE');

      //     this.jtMaterialeDeratizari.map((item: IJTMaterialProcesVerbal) => {
      //       if (item.produs) {
      //         item.produs = this.mapMaterialeDezinsectii.get(item.produs.id)
      //       }
      //     })
      // } else {
      //   console.log("else2");

      // }
      // this.jtMaterialeDezinfectii = procesVerbal.jtmaterialProcesVerbals.filter((item: IJTMaterialProcesVerbal) => item.procedura === 'DEZINFECTIE');

      // this.jtMaterialeDezinsectii[0].produs = this.materialeDezinsectii[0];
      // console.log(this.materialeDezinsectii);

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
      this.subscribeToSaveResponse(
        this.procesVerbalService.update(
          procesVerbal,
          this.jtMaterialeDeratizari.concat(this.jtMaterialeDezinfectii).concat(this.jtMaterialeDezinsectii),
        ),
      );
    } else {
      this.subscribeToSaveResponse(
        this.procesVerbalService.create(
          procesVerbal,
          this.jtMaterialeDeratizari.concat(this.jtMaterialeDezinfectii).concat(this.jtMaterialeDezinsectii),
        ),
      );
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
      procesVerbal: null,
    });
  }

  protected deleteDeratizare(index: number) {
    this.jtMaterialeDeratizari.splice(index, 1);
  }

  protected addDezinsectie() {
    this.jtMaterialeDezinsectii.push({
      id: '4',
      procedura: Procedura.DEZINSECTIE,
      cantitate: null,
      produs: null,
      procesVerbal: null,
    });
  }

  protected deleteDezinsectie(index: number) {
    this.jtMaterialeDezinsectii.splice(index, 1);
  }

  protected addDezinfectie() {
    this.jtMaterialeDezinfectii.push({
      id: '4',
      procedura: Procedura.DEZINFECTIE,
      cantitate: null,
      produs: null,
      procesVerbal: null,
    });
  }

  protected deleteDezinfectie(index: number) {
    this.jtMaterialeDezinfectii.splice(index, 1);
  }
}

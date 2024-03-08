import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SortDirective, SortByDirective } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { FormsModule } from '@angular/forms';
import { SortService } from 'app/shared/sort/sort.service';
import { IProcesVerbal } from '../proces-verbal.model';
import { ProcesVerbalService } from '../service/proces-verbal.service';
import { ToastModule } from 'primeng/toast';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { TagModule } from 'primeng/tag';
import { MessageService } from 'primeng/api';
import { DDDEntitate } from 'app/entities/ddd-entitate';
import { IClient } from 'app/entities/client/client.model';
import { PageableResponse } from 'app/entities/utilizator/service/utilizator.service';

@Component({
  standalone: true,
  selector: 'jhi-proces-verbal',
  templateUrl: './proces-verbal.component.html',
  imports: [
    RouterModule,
    FormsModule,
    SharedModule,
    SortDirective,
    SortByDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    ToastModule,
    TableModule,
    ButtonModule,
    DropdownModule,
    InputTextModule,
    TagModule,
  ],
  providers: [MessageService],
})
export class ProcesVerbalComponent implements OnInit {
  procesVerbals!: DDDEntitate[];
  totalRecords = 0;
  loading: boolean = true;

  constructor(
    protected procesVerbalService: ProcesVerbalService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected modalService: NgbModal,
  ) {}

  trackId = (_index: number, item: IProcesVerbal): string => this.procesVerbalService.getProcesVerbalIdentifier(item);

  ngOnInit(): void {
    this.loading = true;
  }

  protected onResponseSuccess(response: PageableResponse): void {
    if (!response.body) {
      alert('No body');
    } else {
      this.procesVerbals = response.body.content ?? [];
      this.totalRecords = response.body.totalElements;
    }
  }

  loadData(event?: TableLazyLoadEvent) {
    this.loading = true;

    this.procesVerbalService.getList(event).subscribe({
      next: (res: PageableResponse) => {
        this.onResponseSuccess(res);
        this.loading = false;
      },
    });
  }

  onRowEditInit(client: IClient) {}

  onRowEditSave(client: IClient) {}

  onRowEditCancel(client: IClient, index: number) {}
}

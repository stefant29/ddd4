import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SortDirective, SortByDirective } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { FormsModule } from '@angular/forms';
import { SortService } from 'app/shared/sort/sort.service';
import { IClient } from '../client.model';
import { ClientService } from '../service/client.service';
import { ToastModule } from 'primeng/toast';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { TagModule } from 'primeng/tag';
import { DDDEntitate } from 'app/entities/ddd-entitate';
import { PageableResponse } from 'app/entities/utilizator/service/utilizator.service';
import { MessageService, SelectItem } from 'primeng/api';

@Component({
  standalone: true,
  selector: 'jhi-client',
  templateUrl: './client.component.html',
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
export class ClientComponent implements OnInit {
  clients!: DDDEntitate[];
  totalRecords = 0;
  loading: boolean = true;
  categorii!: SelectItem[];

  constructor(
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected modalService: NgbModal,
  ) {}

  trackId = (_index: number, item: IClient): string => this.clientService.getClientIdentifier(item);

  ngOnInit(): void {
    // TODO: get categorii
    // TODO: get categorii
    // TODO: get categorii
    this.categorii = [
      { label: 'TODO', value: 'OPERATOR' },
      { label: 'TODO', value: 'ADMIN' },
    ];

    this.loading = true;
  }

  protected onResponseSuccess(response: PageableResponse): void {
    if (!response.body) {
      alert('No body');
    } else {
      this.clients = response.body.content ?? [];
      this.totalRecords = response.body.totalElements;
    }
  }

  loadData(event?: TableLazyLoadEvent) {
    this.loading = true;

    this.clientService.getList(event).subscribe({
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

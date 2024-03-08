import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SortDirective, SortByDirective } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { FormsModule } from '@angular/forms';
import { SortService } from 'app/shared/sort/sort.service';
import { IMaterial } from '../material.model';
import { MaterialService } from '../service/material.service';
import { ToastModule } from 'primeng/toast';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { TagModule } from 'primeng/tag';
import { MessageService } from 'primeng/api';
import { PageableResponse } from 'app/entities/utilizator/service/utilizator.service';
import { DDDEntitate } from 'app/entities/ddd-entitate';

@Component({
  standalone: true,
  selector: 'jhi-material',
  templateUrl: './material.component.html',
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
export class MaterialComponent implements OnInit {
  materials!: DDDEntitate[];

  totalRecords = 0;
  loading: boolean = true;

  constructor(
    protected materialService: MaterialService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected modalService: NgbModal,
  ) {}

  trackId = (_index: number, item: IMaterial): string => this.materialService.getMaterialIdentifier(item);

  ngOnInit(): void {
    this.loading = true;
  }

  protected onResponseSuccess(response: PageableResponse): void {
    if (!response.body) {
      alert('No body');
    } else {
      this.materials = response.body.content ?? [];
      console.log(this.totalRecords);
      this.totalRecords = response.body.totalElements;
      console.log(this.totalRecords);
    }
  }

  loadData(event?: TableLazyLoadEvent) {
    console.log('event S: ', event);

    this.loading = true;

    this.materialService.getList(event).subscribe({
      next: (res: PageableResponse) => {
        console.log('RES: ', res);

        this.onResponseSuccess(res);
        this.loading = false;
      },
    });
  }

  onRowEditInit(client: IMaterial) {}

  onRowEditSave(client: IMaterial) {}

  onRowEditCancel(client: IMaterial, index: number) {}
}

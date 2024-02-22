import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SortDirective, SortByDirective } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { FormsModule } from '@angular/forms';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { SortService } from 'app/shared/sort/sort.service';
import { IUtilizator } from '../utilizator.model';
import { EntityArrayResponseType, PageableResponse, UtilizatorService } from '../service/utilizator.service';
import { UtilizatorDeleteDialogComponent } from '../delete/utilizator-delete-dialog.component';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { MessageService, SelectItem } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { DDDEntitate } from 'app/entities/ddd-entitate';

@Component({
  standalone: true,
  selector: 'jhi-utilizator',
  templateUrl: './utilizator.component.html',
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
  ],
  providers: [MessageService],
})
export class UtilizatorComponent implements OnInit {
  utilizators!: DDDEntitate[];

  clonedProducts: { [s: string]: IUtilizator } = {};
  functii!: SelectItem[];

  totalRecords = 0;
  loading: boolean = true;

  constructor(
    protected utilizatorService: UtilizatorService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected modalService: NgbModal,
    private messageService: MessageService,
  ) {}

  trackId = (_index: number, item: IUtilizator): string => this.utilizatorService.getUtilizatorIdentifier(item);

  ngOnInit(): void {
    // TODO: get functii
    // TODO: get functii
    // TODO: get functii
    this.functii = [
      { label: 'In Stock', value: 'INSTOCK' },
      { label: 'Low Stock', value: 'LOWSTOCK' },
      { label: 'Out of Stock', value: 'OUTOFSTOCK' },
    ];

    this.loading = true;
  }

  protected onResponseSuccess(response: PageableResponse): void {
    if (!response.body) {
      alert('No body');
    } else {
      this.utilizators = response.body.content ?? [];
      console.log(this.totalRecords);
      this.totalRecords = response.body.totalElements;
      console.log(this.totalRecords);
    }
  }

  onRowEditInit(utilizator: IUtilizator) {
    this.clonedProducts[utilizator.id as string] = { ...utilizator };
  }

  onRowEditSave(utilizator: IUtilizator) {
    // if (utilizator.price > 0) {
    //     delete this.clonedProducts[product.id as string];
    //     this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Product is updated' });
    // } else {
    //     this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Invalid Price' });
    // }
    console.log('utilizator', utilizator);
    alert('edit save');
  }

  onRowEditCancel(utilizator: IUtilizator, index: number) {
    // this.products[index] = this.clonedProducts[product.id as string];
    // delete this.clonedProducts[product.id as string];

    console.log('utilizator', utilizator);
    alert('edit cancel');
  }

  loadCustomers(event?: TableLazyLoadEvent) {
    console.log('event S: ', event);

    this.loading = true;

    setTimeout(() => {
      this.utilizatorService.getList(event).subscribe({
        next: (res: PageableResponse) => {
          console.log('RES: ', res);

          this.onResponseSuccess(res);
          this.loading = false;
        },
      });
    }, 1000);
  }
}

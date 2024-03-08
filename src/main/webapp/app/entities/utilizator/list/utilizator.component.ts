import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SortDirective, SortByDirective } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { FormsModule } from '@angular/forms';
import { SortService } from 'app/shared/sort/sort.service';
import { IUtilizator } from '../utilizator.model';
import { PageableResponse, UtilizatorService } from '../service/utilizator.service';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { MessageService, SelectItem } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { DDDEntitate } from 'app/entities/ddd-entitate';
import { TagModule } from 'primeng/tag';

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
    TagModule,
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
      { label: 'Operator', value: 'OPERATOR' },
      { label: 'Admin', value: 'ADMIN' },
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

  loadData(event?: TableLazyLoadEvent) {
    console.log('event S: ', event);

    this.loading = true;

    this.utilizatorService.getList(event).subscribe({
      next: (res: PageableResponse) => {
        console.log('RES: ', res);

        this.onResponseSuccess(res);
        this.loading = false;
      },
    });
  }

  getSeverity(functie: string) {
    switch (functie) {
      case 'Operator':
        return 'info';

      case 'Admin':
        return 'success';

      default:
        return '';
    }
  }
}

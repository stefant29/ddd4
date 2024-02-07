import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICompanie } from 'app/entities/companie/companie.model';
import { CompanieService } from 'app/entities/companie/service/companie.service';
import { ICategorieClient } from 'app/entities/categorie-client/categorie-client.model';
import { CategorieClientService } from 'app/entities/categorie-client/service/categorie-client.service';
import { ClientService } from '../service/client.service';
import { IClient } from '../client.model';
import { ClientFormService, ClientFormGroup } from './client-form.service';

@Component({
  standalone: true,
  selector: 'jhi-client-update',
  templateUrl: './client-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ClientUpdateComponent implements OnInit {
  isSaving = false;
  client: IClient | null = null;

  companiesSharedCollection: ICompanie[] = [];
  categorieClientsSharedCollection: ICategorieClient[] = [];

  editForm: ClientFormGroup = this.clientFormService.createClientFormGroup();

  constructor(
    protected clientService: ClientService,
    protected clientFormService: ClientFormService,
    protected companieService: CompanieService,
    protected categorieClientService: CategorieClientService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCompanie = (o1: ICompanie | null, o2: ICompanie | null): boolean => this.companieService.compareCompanie(o1, o2);

  compareCategorieClient = (o1: ICategorieClient | null, o2: ICategorieClient | null): boolean =>
    this.categorieClientService.compareCategorieClient(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ client }) => {
      this.client = client;
      if (client) {
        this.updateForm(client);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const client = this.clientFormService.getClient(this.editForm);
    if (client.id !== null) {
      this.subscribeToSaveResponse(this.clientService.update(client));
    } else {
      this.subscribeToSaveResponse(this.clientService.create(client));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClient>>): void {
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

  protected updateForm(client: IClient): void {
    this.client = client;
    this.clientFormService.resetForm(this.editForm, client);

    this.companiesSharedCollection = this.companieService.addCompanieToCollectionIfMissing<ICompanie>(
      this.companiesSharedCollection,
      client.companie,
    );
    this.categorieClientsSharedCollection = this.categorieClientService.addCategorieClientToCollectionIfMissing<ICategorieClient>(
      this.categorieClientsSharedCollection,
      client.categorie,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.companieService
      .query()
      .pipe(map((res: HttpResponse<ICompanie[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompanie[]) => this.companieService.addCompanieToCollectionIfMissing<ICompanie>(companies, this.client?.companie)),
      )
      .subscribe((companies: ICompanie[]) => (this.companiesSharedCollection = companies));

    this.categorieClientService
      .query()
      .pipe(map((res: HttpResponse<ICategorieClient[]>) => res.body ?? []))
      .pipe(
        map((categorieClients: ICategorieClient[]) =>
          this.categorieClientService.addCategorieClientToCollectionIfMissing<ICategorieClient>(categorieClients, this.client?.categorie),
        ),
      )
      .subscribe((categorieClients: ICategorieClient[]) => (this.categorieClientsSharedCollection = categorieClients));
  }
}

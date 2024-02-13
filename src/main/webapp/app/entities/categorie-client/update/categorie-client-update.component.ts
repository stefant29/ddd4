import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICategorieClient } from '../categorie-client.model';
import { CategorieClientService } from '../service/categorie-client.service';
import { CategorieClientFormService, CategorieClientFormGroup } from './categorie-client-form.service';

@Component({
  standalone: true,
  selector: 'jhi-categorie-client-update',
  templateUrl: './categorie-client-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CategorieClientUpdateComponent implements OnInit {
  isSaving = false;
  categorieClient: ICategorieClient | null = null;

  editForm: CategorieClientFormGroup = this.categorieClientFormService.createCategorieClientFormGroup();

  constructor(
    protected categorieClientService: CategorieClientService,
    protected categorieClientFormService: CategorieClientFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieClient }) => {
      this.categorieClient = categorieClient;
      if (categorieClient) {
        this.updateForm(categorieClient);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categorieClient = this.categorieClientFormService.getCategorieClient(this.editForm);
    if (categorieClient.id !== null) {
      this.subscribeToSaveResponse(this.categorieClientService.update(categorieClient));
    } else {
      this.subscribeToSaveResponse(this.categorieClientService.create(categorieClient));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorieClient>>): void {
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

  protected updateForm(categorieClient: ICategorieClient): void {
    this.categorieClient = categorieClient;
    this.categorieClientFormService.resetForm(this.editForm, categorieClient);
  }
}

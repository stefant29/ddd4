import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICompanie } from '../companie.model';
import { CompanieService } from '../service/companie.service';
import { CompanieFormService, CompanieFormGroup } from './companie-form.service';

@Component({
  standalone: true,
  selector: 'jhi-companie-update',
  templateUrl: './companie-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CompanieUpdateComponent implements OnInit {
  isSaving = false;
  companie: ICompanie | null = null;

  editForm: CompanieFormGroup = this.companieFormService.createCompanieFormGroup();

  constructor(
    protected companieService: CompanieService,
    protected companieFormService: CompanieFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companie }) => {
      this.companie = companie;
      if (companie) {
        this.updateForm(companie);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const companie = this.companieFormService.getCompanie(this.editForm);
    if (companie.id !== null) {
      this.subscribeToSaveResponse(this.companieService.update(companie));
    } else {
      this.subscribeToSaveResponse(this.companieService.create(companie));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanie>>): void {
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

  protected updateForm(companie: ICompanie): void {
    this.companie = companie;
    this.companieFormService.resetForm(this.editForm, companie);
  }
}

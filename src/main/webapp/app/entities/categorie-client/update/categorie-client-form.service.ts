import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICategorieClient, NewCategorieClient } from '../categorie-client.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICategorieClient for edit and NewCategorieClientFormGroupInput for create.
 */
type CategorieClientFormGroupInput = ICategorieClient | PartialWithRequiredKeyOf<NewCategorieClient>;

type CategorieClientFormDefaults = Pick<NewCategorieClient, 'id'>;

type CategorieClientFormGroupContent = {
  id: FormControl<ICategorieClient['id'] | NewCategorieClient['id']>;
  nume: FormControl<ICategorieClient['nume']>;
};

export type CategorieClientFormGroup = FormGroup<CategorieClientFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CategorieClientFormService {
  createCategorieClientFormGroup(categorieClient: CategorieClientFormGroupInput = { id: null }): CategorieClientFormGroup {
    const categorieClientRawValue = {
      ...this.getFormDefaults(),
      ...categorieClient,
    };
    return new FormGroup<CategorieClientFormGroupContent>({
      id: new FormControl(
        { value: categorieClientRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nume: new FormControl(categorieClientRawValue.nume),
    });
  }

  getCategorieClient(form: CategorieClientFormGroup): ICategorieClient | NewCategorieClient {
    return form.getRawValue() as ICategorieClient | NewCategorieClient;
  }

  resetForm(form: CategorieClientFormGroup, categorieClient: CategorieClientFormGroupInput): void {
    const categorieClientRawValue = { ...this.getFormDefaults(), ...categorieClient };
    form.reset(
      {
        ...categorieClientRawValue,
        id: { value: categorieClientRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CategorieClientFormDefaults {
    return {
      id: null,
    };
  }
}

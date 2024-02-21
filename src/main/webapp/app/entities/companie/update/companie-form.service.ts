import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICompanie, NewCompanie } from '../companie.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICompanie for edit and NewCompanieFormGroupInput for create.
 */
type CompanieFormGroupInput = ICompanie | PartialWithRequiredKeyOf<NewCompanie>;

type CompanieFormDefaults = Pick<NewCompanie, 'id'>;

type CompanieFormGroupContent = {
  id: FormControl<ICompanie['id'] | NewCompanie['id']>;
  nume: FormControl<ICompanie['nume']>;
};

export type CompanieFormGroup = FormGroup<CompanieFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CompanieFormService {
  createCompanieFormGroup(companie: CompanieFormGroupInput = { id: null }): CompanieFormGroup {
    const companieRawValue = {
      ...this.getFormDefaults(),
      ...companie,
    };
    return new FormGroup<CompanieFormGroupContent>({
      id: new FormControl(
        { value: companieRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nume: new FormControl(companieRawValue.nume),
    });
  }

  getCompanie(form: CompanieFormGroup): ICompanie | NewCompanie {
    return form.getRawValue() as ICompanie | NewCompanie;
  }

  resetForm(form: CompanieFormGroup, companie: CompanieFormGroupInput): void {
    const companieRawValue = { ...this.getFormDefaults(), ...companie };
    form.reset(
      {
        ...companieRawValue,
        id: { value: companieRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CompanieFormDefaults {
    return {
      id: null,
    };
  }
}

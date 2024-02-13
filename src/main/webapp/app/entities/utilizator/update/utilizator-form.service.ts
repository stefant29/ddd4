import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IUtilizator, NewUtilizator } from '../utilizator.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUtilizator for edit and NewUtilizatorFormGroupInput for create.
 */
type UtilizatorFormGroupInput = IUtilizator | PartialWithRequiredKeyOf<NewUtilizator>;

type UtilizatorFormDefaults = Pick<NewUtilizator, 'id'>;

type UtilizatorFormGroupContent = {
  id: FormControl<IUtilizator['id'] | NewUtilizator['id']>;
  nume: FormControl<IUtilizator['nume']>;
  prenume: FormControl<IUtilizator['prenume']>;
  functie: FormControl<IUtilizator['functie']>;
  user: FormControl<IUtilizator['user']>;
  companie: FormControl<IUtilizator['companie']>;
};

export type UtilizatorFormGroup = FormGroup<UtilizatorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UtilizatorFormService {
  createUtilizatorFormGroup(utilizator: UtilizatorFormGroupInput = { id: null }): UtilizatorFormGroup {
    const utilizatorRawValue = {
      ...this.getFormDefaults(),
      ...utilizator,
    };
    return new FormGroup<UtilizatorFormGroupContent>({
      id: new FormControl(
        { value: utilizatorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nume: new FormControl(utilizatorRawValue.nume, {
        validators: [Validators.required],
      }),
      prenume: new FormControl(utilizatorRawValue.prenume, {
        validators: [Validators.required],
      }),
      functie: new FormControl(utilizatorRawValue.functie, {
        validators: [Validators.required],
      }),
      user: new FormControl(utilizatorRawValue.user, {
        validators: [Validators.required],
      }),
      companie: new FormControl(utilizatorRawValue.companie, {
        validators: [Validators.required],
      }),
    });
  }

  getUtilizator(form: UtilizatorFormGroup): IUtilizator | NewUtilizator {
    return form.getRawValue() as IUtilizator | NewUtilizator;
  }

  resetForm(form: UtilizatorFormGroup, utilizator: UtilizatorFormGroupInput): void {
    const utilizatorRawValue = { ...this.getFormDefaults(), ...utilizator };
    form.reset(
      {
        ...utilizatorRawValue,
        id: { value: utilizatorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): UtilizatorFormDefaults {
    return {
      id: null,
    };
  }
}

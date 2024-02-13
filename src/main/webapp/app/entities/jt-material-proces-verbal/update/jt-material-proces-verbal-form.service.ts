import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IJTMaterialProcesVerbal, NewJTMaterialProcesVerbal } from '../jt-material-proces-verbal.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IJTMaterialProcesVerbal for edit and NewJTMaterialProcesVerbalFormGroupInput for create.
 */
type JTMaterialProcesVerbalFormGroupInput = IJTMaterialProcesVerbal | PartialWithRequiredKeyOf<NewJTMaterialProcesVerbal>;

type JTMaterialProcesVerbalFormDefaults = Pick<NewJTMaterialProcesVerbal, 'id'>;

type JTMaterialProcesVerbalFormGroupContent = {
  id: FormControl<IJTMaterialProcesVerbal['id'] | NewJTMaterialProcesVerbal['id']>;
  procedura: FormControl<IJTMaterialProcesVerbal['procedura']>;
  cantitate: FormControl<IJTMaterialProcesVerbal['cantitate']>;
  produs: FormControl<IJTMaterialProcesVerbal['produs']>;
  procesVerbal: FormControl<IJTMaterialProcesVerbal['procesVerbal']>;
};

export type JTMaterialProcesVerbalFormGroup = FormGroup<JTMaterialProcesVerbalFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class JTMaterialProcesVerbalFormService {
  createJTMaterialProcesVerbalFormGroup(
    jTMaterialProcesVerbal: JTMaterialProcesVerbalFormGroupInput = { id: null },
  ): JTMaterialProcesVerbalFormGroup {
    const jTMaterialProcesVerbalRawValue = {
      ...this.getFormDefaults(),
      ...jTMaterialProcesVerbal,
    };
    return new FormGroup<JTMaterialProcesVerbalFormGroupContent>({
      id: new FormControl(
        { value: jTMaterialProcesVerbalRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      procedura: new FormControl(jTMaterialProcesVerbalRawValue.procedura),
      cantitate: new FormControl(jTMaterialProcesVerbalRawValue.cantitate),
      produs: new FormControl(jTMaterialProcesVerbalRawValue.produs, {
        validators: [Validators.required],
      }),
      procesVerbal: new FormControl(jTMaterialProcesVerbalRawValue.procesVerbal, {
        validators: [Validators.required],
      }),
    });
  }

  getJTMaterialProcesVerbal(form: JTMaterialProcesVerbalFormGroup): IJTMaterialProcesVerbal | NewJTMaterialProcesVerbal {
    return form.getRawValue() as IJTMaterialProcesVerbal | NewJTMaterialProcesVerbal;
  }

  resetForm(form: JTMaterialProcesVerbalFormGroup, jTMaterialProcesVerbal: JTMaterialProcesVerbalFormGroupInput): void {
    const jTMaterialProcesVerbalRawValue = { ...this.getFormDefaults(), ...jTMaterialProcesVerbal };
    form.reset(
      {
        ...jTMaterialProcesVerbalRawValue,
        id: { value: jTMaterialProcesVerbalRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): JTMaterialProcesVerbalFormDefaults {
    return {
      id: null,
    };
  }
}

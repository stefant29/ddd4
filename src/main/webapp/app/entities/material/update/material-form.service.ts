import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMaterial, NewMaterial } from '../material.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMaterial for edit and NewMaterialFormGroupInput for create.
 */
type MaterialFormGroupInput = IMaterial | PartialWithRequiredKeyOf<NewMaterial>;

type MaterialFormDefaults = Pick<NewMaterial, 'id'>;

type MaterialFormGroupContent = {
  id: FormControl<IMaterial['id'] | NewMaterial['id']>;
  procedura: FormControl<IMaterial['procedura']>;
  factura: FormControl<IMaterial['factura']>;
  denumire: FormControl<IMaterial['denumire']>;
  lot: FormControl<IMaterial['lot']>;
  dataAchizitionare: FormControl<IMaterial['dataAchizitionare']>;
  dataExpirare: FormControl<IMaterial['dataExpirare']>;
  dilutie: FormControl<IMaterial['dilutie']>;
  timpContact: FormControl<IMaterial['timpContact']>;
  metodaAplicare: FormControl<IMaterial['metodaAplicare']>;
  gramaj: FormControl<IMaterial['gramaj']>;
  cantitate: FormControl<IMaterial['cantitate']>;
};

export type MaterialFormGroup = FormGroup<MaterialFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MaterialFormService {
  createMaterialFormGroup(material: MaterialFormGroupInput = { id: null }): MaterialFormGroup {
    const materialRawValue = {
      ...this.getFormDefaults(),
      ...material,
    };
    return new FormGroup<MaterialFormGroupContent>({
      id: new FormControl({ value: materialRawValue.id, disabled: true }),
      procedura: new FormControl(materialRawValue.procedura),
      factura: new FormControl(materialRawValue.factura, {
        validators: [Validators.required],
      }),
      denumire: new FormControl(materialRawValue.denumire, {
        validators: [Validators.required],
      }),
      lot: new FormControl(materialRawValue.lot, {
        validators: [Validators.required],
      }),
      dataAchizitionare: new FormControl(materialRawValue.dataAchizitionare),
      dataExpirare: new FormControl(materialRawValue.dataExpirare),
      dilutie: new FormControl(materialRawValue.dilutie),
      timpContact: new FormControl(materialRawValue.timpContact),
      metodaAplicare: new FormControl(materialRawValue.metodaAplicare),
      gramaj: new FormControl(materialRawValue.gramaj),
      cantitate: new FormControl(materialRawValue.cantitate),
    });
  }

  getMaterial(form: MaterialFormGroup): IMaterial | NewMaterial {
    return form.getRawValue() as IMaterial | NewMaterial;
  }

  resetForm(form: MaterialFormGroup, material: MaterialFormGroupInput): void {
    const materialRawValue = { ...this.getFormDefaults(), ...material };
    form.reset(
      {
        ...materialRawValue,
        id: { value: materialRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MaterialFormDefaults {
    return {
      id: null,
    };
  }
}

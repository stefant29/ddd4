import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IClient, NewClient } from '../client.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IClient for edit and NewClientFormGroupInput for create.
 */
type ClientFormGroupInput = IClient | PartialWithRequiredKeyOf<NewClient>;

type ClientFormDefaults = Pick<NewClient, 'id' | 'deratizare' | 'dezinsectie' | 'dezinfectie'>;

type ClientFormGroupContent = {
  id: FormControl<IClient['id'] | NewClient['id']>;
  denumire: FormControl<IClient['denumire']>;
  codFiscal: FormControl<IClient['codFiscal']>;
  numarRegistruComert: FormControl<IClient['numarRegistruComert']>;
  adresaPunctLucru: FormControl<IClient['adresaPunctLucru']>;
  telefon: FormControl<IClient['telefon']>;
  email: FormControl<IClient['email']>;
  persoanaContact: FormControl<IClient['persoanaContact']>;
  contract: FormControl<IClient['contract']>;
  deratizare: FormControl<IClient['deratizare']>;
  dezinsectie: FormControl<IClient['dezinsectie']>;
  dezinfectie: FormControl<IClient['dezinfectie']>;
  frecventaDeratizare: FormControl<IClient['frecventaDeratizare']>;
  frecventaDezinsectie: FormControl<IClient['frecventaDezinsectie']>;
  frecventaDezinfectie: FormControl<IClient['frecventaDezinfectie']>;
  companie: FormControl<IClient['companie']>;
  categorie: FormControl<IClient['categorie']>;
};

export type ClientFormGroup = FormGroup<ClientFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ClientFormService {
  createClientFormGroup(client: ClientFormGroupInput = { id: null }): ClientFormGroup {
    const clientRawValue = {
      ...this.getFormDefaults(),
      ...client,
    };
    return new FormGroup<ClientFormGroupContent>({
      id: new FormControl(
        { value: clientRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      denumire: new FormControl(clientRawValue.denumire, {
        validators: [Validators.required],
      }),
      codFiscal: new FormControl(clientRawValue.codFiscal, {
        validators: [Validators.required],
      }),
      numarRegistruComert: new FormControl(clientRawValue.numarRegistruComert),
      adresaPunctLucru: new FormControl(clientRawValue.adresaPunctLucru, {
        validators: [Validators.required],
      }),
      telefon: new FormControl(clientRawValue.telefon, {
        validators: [Validators.required],
      }),
      email: new FormControl(clientRawValue.email),
      persoanaContact: new FormControl(clientRawValue.persoanaContact, {
        validators: [Validators.required],
      }),
      contract: new FormControl(clientRawValue.contract),
      deratizare: new FormControl(clientRawValue.deratizare),
      dezinsectie: new FormControl(clientRawValue.dezinsectie),
      dezinfectie: new FormControl(clientRawValue.dezinfectie),
      frecventaDeratizare: new FormControl(clientRawValue.frecventaDeratizare),
      frecventaDezinsectie: new FormControl(clientRawValue.frecventaDezinsectie),
      frecventaDezinfectie: new FormControl(clientRawValue.frecventaDezinfectie),
      companie: new FormControl(clientRawValue.companie, {
        validators: [Validators.required],
      }),
      categorie: new FormControl(clientRawValue.categorie),
    });
  }

  getClient(form: ClientFormGroup): IClient | NewClient {
    return form.getRawValue() as IClient | NewClient;
  }

  resetForm(form: ClientFormGroup, client: ClientFormGroupInput): void {
    const clientRawValue = { ...this.getFormDefaults(), ...client };
    form.reset(
      {
        ...clientRawValue,
        id: { value: clientRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ClientFormDefaults {
    return {
      id: null,
      deratizare: false,
      dezinsectie: false,
      dezinfectie: false,
    };
  }
}

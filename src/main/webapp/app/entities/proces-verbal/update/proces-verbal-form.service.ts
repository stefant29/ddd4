import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProcesVerbal, NewProcesVerbal } from '../proces-verbal.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProcesVerbal for edit and NewProcesVerbalFormGroupInput for create.
 */
type ProcesVerbalFormGroupInput = IProcesVerbal | PartialWithRequiredKeyOf<NewProcesVerbal>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProcesVerbal | NewProcesVerbal> = Omit<T, 'ora'> & {
  ora?: string | null;
};

type ProcesVerbalFormRawValue = FormValueOf<IProcesVerbal>;

type NewProcesVerbalFormRawValue = FormValueOf<NewProcesVerbal>;

type ProcesVerbalFormDefaults = Pick<NewProcesVerbal, 'id' | 'ora' | 'garantieDezinsectie' | 'garantieDeratizare'>;

type ProcesVerbalFormGroupContent = {
  ora: FormControl<ProcesVerbalFormRawValue['ora']>;
  numarProcesVerbal: FormControl<ProcesVerbalFormRawValue['numarProcesVerbal']>;
  reprezentant: FormControl<ProcesVerbalFormRawValue['reprezentant']>;
  spatii: FormControl<ProcesVerbalFormRawValue['spatii']>;
  suprafata: FormControl<ProcesVerbalFormRawValue['suprafata']>;
  rapelDezinsectie: FormControl<ProcesVerbalFormRawValue['rapelDezinsectie']>;
  rapelDeratizare: FormControl<ProcesVerbalFormRawValue['rapelDeratizare']>;
  garantieDezinsectie: FormControl<ProcesVerbalFormRawValue['garantieDezinsectie']>;
  garantieDeratizare: FormControl<ProcesVerbalFormRawValue['garantieDeratizare']>;
  client: FormControl<ProcesVerbalFormRawValue['client']>;
  operator: FormControl<ProcesVerbalFormRawValue['operator']>;
};

export type ProcesVerbalFormGroup = FormGroup<ProcesVerbalFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProcesVerbalFormService {
  createProcesVerbalFormGroup(procesVerbal: ProcesVerbalFormGroupInput = { id: null }): ProcesVerbalFormGroup {
    const procesVerbalRawValue = this.convertProcesVerbalToProcesVerbalRawValue({
      ...this.getFormDefaults(),
      ...procesVerbal,
    });
    return new FormGroup<ProcesVerbalFormGroupContent>({
      ora: new FormControl(procesVerbalRawValue.ora, {
        validators: [Validators.required],
      }),
      numarProcesVerbal: new FormControl(procesVerbalRawValue.numarProcesVerbal, {
        validators: [Validators.required],
      }),
      reprezentant: new FormControl(procesVerbalRawValue.reprezentant),
      spatii: new FormControl(procesVerbalRawValue.spatii),
      suprafata: new FormControl(procesVerbalRawValue.suprafata),
      rapelDezinsectie: new FormControl(procesVerbalRawValue.rapelDezinsectie),
      rapelDeratizare: new FormControl(procesVerbalRawValue.rapelDeratizare),
      garantieDezinsectie: new FormControl(procesVerbalRawValue.garantieDezinsectie),
      garantieDeratizare: new FormControl(procesVerbalRawValue.garantieDeratizare),
      client: new FormControl(procesVerbalRawValue.client, {
        validators: [Validators.required],
      }),
      operator: new FormControl(procesVerbalRawValue.operator, {
        validators: [Validators.required],
      }),
    });
  }

  getProcesVerbal(form: ProcesVerbalFormGroup): IProcesVerbal | NewProcesVerbal {
    return this.convertProcesVerbalRawValueToProcesVerbal(form.getRawValue() as ProcesVerbalFormRawValue | NewProcesVerbalFormRawValue);
  }

  resetForm(form: ProcesVerbalFormGroup, procesVerbal: ProcesVerbalFormGroupInput): void {
    const procesVerbalRawValue = this.convertProcesVerbalToProcesVerbalRawValue({ ...this.getFormDefaults(), ...procesVerbal });
    form.reset(
      {
        ...procesVerbalRawValue,
        id: { value: procesVerbalRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProcesVerbalFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      ora: currentTime,
      garantieDezinsectie: false,
      garantieDeratizare: false,
    };
  }

  private convertProcesVerbalRawValueToProcesVerbal(
    rawProcesVerbal: ProcesVerbalFormRawValue | NewProcesVerbalFormRawValue,
  ): IProcesVerbal | NewProcesVerbal {
    return {
      ...rawProcesVerbal,
      ora: dayjs(rawProcesVerbal.ora, DATE_TIME_FORMAT),
    };
  }

  private convertProcesVerbalToProcesVerbalRawValue(
    procesVerbal: IProcesVerbal | (Partial<NewProcesVerbal> & ProcesVerbalFormDefaults),
  ): ProcesVerbalFormRawValue | PartialWithRequiredKeyOf<NewProcesVerbalFormRawValue> {
    return {
      ...procesVerbal,
      ora: procesVerbal.ora ? procesVerbal.ora.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}

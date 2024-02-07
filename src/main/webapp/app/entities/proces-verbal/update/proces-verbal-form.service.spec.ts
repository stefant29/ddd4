import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../proces-verbal.test-samples';

import { ProcesVerbalFormService } from './proces-verbal-form.service';

describe('ProcesVerbal Form Service', () => {
  let service: ProcesVerbalFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProcesVerbalFormService);
  });

  describe('Service methods', () => {
    describe('createProcesVerbalFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProcesVerbalFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            data: expect.any(Object),
            ora: expect.any(Object),
            numarProcesVerbal: expect.any(Object),
            reprezentant: expect.any(Object),
            spatii: expect.any(Object),
            suprafata: expect.any(Object),
            rapelDezinsectie: expect.any(Object),
            rapelDeratizare: expect.any(Object),
            garantieDezinsectie: expect.any(Object),
            garantieDeratizare: expect.any(Object),
            companie: expect.any(Object),
            client: expect.any(Object),
            operator: expect.any(Object),
          }),
        );
      });

      it('passing IProcesVerbal should create a new form with FormGroup', () => {
        const formGroup = service.createProcesVerbalFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            data: expect.any(Object),
            ora: expect.any(Object),
            numarProcesVerbal: expect.any(Object),
            reprezentant: expect.any(Object),
            spatii: expect.any(Object),
            suprafata: expect.any(Object),
            rapelDezinsectie: expect.any(Object),
            rapelDeratizare: expect.any(Object),
            garantieDezinsectie: expect.any(Object),
            garantieDeratizare: expect.any(Object),
            companie: expect.any(Object),
            client: expect.any(Object),
            operator: expect.any(Object),
          }),
        );
      });
    });

    describe('getProcesVerbal', () => {
      it('should return NewProcesVerbal for default ProcesVerbal initial value', () => {
        const formGroup = service.createProcesVerbalFormGroup(sampleWithNewData);

        const procesVerbal = service.getProcesVerbal(formGroup) as any;

        expect(procesVerbal).toMatchObject(sampleWithNewData);
      });

      it('should return NewProcesVerbal for empty ProcesVerbal initial value', () => {
        const formGroup = service.createProcesVerbalFormGroup();

        const procesVerbal = service.getProcesVerbal(formGroup) as any;

        expect(procesVerbal).toMatchObject({});
      });

      it('should return IProcesVerbal', () => {
        const formGroup = service.createProcesVerbalFormGroup(sampleWithRequiredData);

        const procesVerbal = service.getProcesVerbal(formGroup) as any;

        expect(procesVerbal).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProcesVerbal should not enable id FormControl', () => {
        const formGroup = service.createProcesVerbalFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProcesVerbal should disable id FormControl', () => {
        const formGroup = service.createProcesVerbalFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

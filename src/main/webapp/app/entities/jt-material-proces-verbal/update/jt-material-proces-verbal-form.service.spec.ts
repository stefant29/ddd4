import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../jt-material-proces-verbal.test-samples';

import { JTMaterialProcesVerbalFormService } from './jt-material-proces-verbal-form.service';

describe('JTMaterialProcesVerbal Form Service', () => {
  let service: JTMaterialProcesVerbalFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JTMaterialProcesVerbalFormService);
  });

  describe('Service methods', () => {
    describe('createJTMaterialProcesVerbalFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createJTMaterialProcesVerbalFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            procedura: expect.any(Object),
            cantitate: expect.any(Object),
            produs: expect.any(Object),
            procesVerbal: expect.any(Object),
          }),
        );
      });

      it('passing IJTMaterialProcesVerbal should create a new form with FormGroup', () => {
        const formGroup = service.createJTMaterialProcesVerbalFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            procedura: expect.any(Object),
            cantitate: expect.any(Object),
            produs: expect.any(Object),
            procesVerbal: expect.any(Object),
          }),
        );
      });
    });

    describe('getJTMaterialProcesVerbal', () => {
      it('should return NewJTMaterialProcesVerbal for default JTMaterialProcesVerbal initial value', () => {
        const formGroup = service.createJTMaterialProcesVerbalFormGroup(sampleWithNewData);

        const jTMaterialProcesVerbal = service.getJTMaterialProcesVerbal(formGroup) as any;

        expect(jTMaterialProcesVerbal).toMatchObject(sampleWithNewData);
      });

      it('should return NewJTMaterialProcesVerbal for empty JTMaterialProcesVerbal initial value', () => {
        const formGroup = service.createJTMaterialProcesVerbalFormGroup();

        const jTMaterialProcesVerbal = service.getJTMaterialProcesVerbal(formGroup) as any;

        expect(jTMaterialProcesVerbal).toMatchObject({});
      });

      it('should return IJTMaterialProcesVerbal', () => {
        const formGroup = service.createJTMaterialProcesVerbalFormGroup(sampleWithRequiredData);

        const jTMaterialProcesVerbal = service.getJTMaterialProcesVerbal(formGroup) as any;

        expect(jTMaterialProcesVerbal).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IJTMaterialProcesVerbal should not enable id FormControl', () => {
        const formGroup = service.createJTMaterialProcesVerbalFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewJTMaterialProcesVerbal should disable id FormControl', () => {
        const formGroup = service.createJTMaterialProcesVerbalFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

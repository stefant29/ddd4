import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../utilizator.test-samples';

import { UtilizatorFormService } from './utilizator-form.service';

describe('Utilizator Form Service', () => {
  let service: UtilizatorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UtilizatorFormService);
  });

  describe('Service methods', () => {
    describe('createUtilizatorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUtilizatorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nume: expect.any(Object),
            prenume: expect.any(Object),
            functie: expect.any(Object),
            companie: expect.any(Object),
          }),
        );
      });

      it('passing IUtilizator should create a new form with FormGroup', () => {
        const formGroup = service.createUtilizatorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nume: expect.any(Object),
            prenume: expect.any(Object),
            functie: expect.any(Object),
            companie: expect.any(Object),
          }),
        );
      });
    });

    describe('getUtilizator', () => {
      it('should return NewUtilizator for default Utilizator initial value', () => {
        const formGroup = service.createUtilizatorFormGroup(sampleWithNewData);

        const utilizator = service.getUtilizator(formGroup) as any;

        expect(utilizator).toMatchObject(sampleWithNewData);
      });

      it('should return NewUtilizator for empty Utilizator initial value', () => {
        const formGroup = service.createUtilizatorFormGroup();

        const utilizator = service.getUtilizator(formGroup) as any;

        expect(utilizator).toMatchObject({});
      });

      it('should return IUtilizator', () => {
        const formGroup = service.createUtilizatorFormGroup(sampleWithRequiredData);

        const utilizator = service.getUtilizator(formGroup) as any;

        expect(utilizator).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUtilizator should not enable id FormControl', () => {
        const formGroup = service.createUtilizatorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUtilizator should disable id FormControl', () => {
        const formGroup = service.createUtilizatorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

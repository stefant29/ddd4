import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../companie.test-samples';

import { CompanieFormService } from './companie-form.service';

describe('Companie Form Service', () => {
  let service: CompanieFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompanieFormService);
  });

  describe('Service methods', () => {
    describe('createCompanieFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCompanieFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nume: expect.any(Object),
          }),
        );
      });

      it('passing ICompanie should create a new form with FormGroup', () => {
        const formGroup = service.createCompanieFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nume: expect.any(Object),
          }),
        );
      });
    });

    describe('getCompanie', () => {
      it('should return NewCompanie for default Companie initial value', () => {
        const formGroup = service.createCompanieFormGroup(sampleWithNewData);

        const companie = service.getCompanie(formGroup) as any;

        expect(companie).toMatchObject(sampleWithNewData);
      });

      it('should return NewCompanie for empty Companie initial value', () => {
        const formGroup = service.createCompanieFormGroup();

        const companie = service.getCompanie(formGroup) as any;

        expect(companie).toMatchObject({});
      });

      it('should return ICompanie', () => {
        const formGroup = service.createCompanieFormGroup(sampleWithRequiredData);

        const companie = service.getCompanie(formGroup) as any;

        expect(companie).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICompanie should not enable id FormControl', () => {
        const formGroup = service.createCompanieFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCompanie should disable id FormControl', () => {
        const formGroup = service.createCompanieFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

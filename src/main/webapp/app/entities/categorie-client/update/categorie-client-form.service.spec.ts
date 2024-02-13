import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../categorie-client.test-samples';

import { CategorieClientFormService } from './categorie-client-form.service';

describe('CategorieClient Form Service', () => {
  let service: CategorieClientFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategorieClientFormService);
  });

  describe('Service methods', () => {
    describe('createCategorieClientFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCategorieClientFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nume: expect.any(Object),
          }),
        );
      });

      it('passing ICategorieClient should create a new form with FormGroup', () => {
        const formGroup = service.createCategorieClientFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nume: expect.any(Object),
          }),
        );
      });
    });

    describe('getCategorieClient', () => {
      it('should return NewCategorieClient for default CategorieClient initial value', () => {
        const formGroup = service.createCategorieClientFormGroup(sampleWithNewData);

        const categorieClient = service.getCategorieClient(formGroup) as any;

        expect(categorieClient).toMatchObject(sampleWithNewData);
      });

      it('should return NewCategorieClient for empty CategorieClient initial value', () => {
        const formGroup = service.createCategorieClientFormGroup();

        const categorieClient = service.getCategorieClient(formGroup) as any;

        expect(categorieClient).toMatchObject({});
      });

      it('should return ICategorieClient', () => {
        const formGroup = service.createCategorieClientFormGroup(sampleWithRequiredData);

        const categorieClient = service.getCategorieClient(formGroup) as any;

        expect(categorieClient).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICategorieClient should not enable id FormControl', () => {
        const formGroup = service.createCategorieClientFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCategorieClient should disable id FormControl', () => {
        const formGroup = service.createCategorieClientFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

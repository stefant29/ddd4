import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IMaterial } from '../material.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../material.test-samples';

import { MaterialService, RestMaterial } from './material.service';

const requireRestSample: RestMaterial = {
  ...sampleWithRequiredData,
  dataAchizitionare: sampleWithRequiredData.dataAchizitionare?.format(DATE_FORMAT),
  dataExpirare: sampleWithRequiredData.dataExpirare?.format(DATE_FORMAT),
};

describe('Material Service', () => {
  let service: MaterialService;
  let httpMock: HttpTestingController;
  let expectedResult: IMaterial | IMaterial[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MaterialService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Material', () => {
      const material = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(material).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Material', () => {
      const material = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(material).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Material', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Material', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Material', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMaterialToCollectionIfMissing', () => {
      it('should add a Material to an empty array', () => {
        const material: IMaterial = sampleWithRequiredData;
        expectedResult = service.addMaterialToCollectionIfMissing([], material);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(material);
      });

      it('should not add a Material to an array that contains it', () => {
        const material: IMaterial = sampleWithRequiredData;
        const materialCollection: IMaterial[] = [
          {
            ...material,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMaterialToCollectionIfMissing(materialCollection, material);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Material to an array that doesn't contain it", () => {
        const material: IMaterial = sampleWithRequiredData;
        const materialCollection: IMaterial[] = [sampleWithPartialData];
        expectedResult = service.addMaterialToCollectionIfMissing(materialCollection, material);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(material);
      });

      it('should add only unique Material to an array', () => {
        const materialArray: IMaterial[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const materialCollection: IMaterial[] = [sampleWithRequiredData];
        expectedResult = service.addMaterialToCollectionIfMissing(materialCollection, ...materialArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const material: IMaterial = sampleWithRequiredData;
        const material2: IMaterial = sampleWithPartialData;
        expectedResult = service.addMaterialToCollectionIfMissing([], material, material2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(material);
        expect(expectedResult).toContain(material2);
      });

      it('should accept null and undefined values', () => {
        const material: IMaterial = sampleWithRequiredData;
        expectedResult = service.addMaterialToCollectionIfMissing([], null, material, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(material);
      });

      it('should return initial array if no Material is added', () => {
        const materialCollection: IMaterial[] = [sampleWithRequiredData];
        expectedResult = service.addMaterialToCollectionIfMissing(materialCollection, undefined, null);
        expect(expectedResult).toEqual(materialCollection);
      });
    });

    describe('compareMaterial', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMaterial(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareMaterial(entity1, entity2);
        const compareResult2 = service.compareMaterial(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareMaterial(entity1, entity2);
        const compareResult2 = service.compareMaterial(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareMaterial(entity1, entity2);
        const compareResult2 = service.compareMaterial(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

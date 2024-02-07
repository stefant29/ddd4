import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IJTMaterialProcesVerbal } from '../jt-material-proces-verbal.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../jt-material-proces-verbal.test-samples';

import { JTMaterialProcesVerbalService } from './jt-material-proces-verbal.service';

const requireRestSample: IJTMaterialProcesVerbal = {
  ...sampleWithRequiredData,
};

describe('JTMaterialProcesVerbal Service', () => {
  let service: JTMaterialProcesVerbalService;
  let httpMock: HttpTestingController;
  let expectedResult: IJTMaterialProcesVerbal | IJTMaterialProcesVerbal[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(JTMaterialProcesVerbalService);
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

    it('should create a JTMaterialProcesVerbal', () => {
      const jTMaterialProcesVerbal = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(jTMaterialProcesVerbal).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a JTMaterialProcesVerbal', () => {
      const jTMaterialProcesVerbal = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(jTMaterialProcesVerbal).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a JTMaterialProcesVerbal', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of JTMaterialProcesVerbal', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a JTMaterialProcesVerbal', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addJTMaterialProcesVerbalToCollectionIfMissing', () => {
      it('should add a JTMaterialProcesVerbal to an empty array', () => {
        const jTMaterialProcesVerbal: IJTMaterialProcesVerbal = sampleWithRequiredData;
        expectedResult = service.addJTMaterialProcesVerbalToCollectionIfMissing([], jTMaterialProcesVerbal);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(jTMaterialProcesVerbal);
      });

      it('should not add a JTMaterialProcesVerbal to an array that contains it', () => {
        const jTMaterialProcesVerbal: IJTMaterialProcesVerbal = sampleWithRequiredData;
        const jTMaterialProcesVerbalCollection: IJTMaterialProcesVerbal[] = [
          {
            ...jTMaterialProcesVerbal,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addJTMaterialProcesVerbalToCollectionIfMissing(jTMaterialProcesVerbalCollection, jTMaterialProcesVerbal);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a JTMaterialProcesVerbal to an array that doesn't contain it", () => {
        const jTMaterialProcesVerbal: IJTMaterialProcesVerbal = sampleWithRequiredData;
        const jTMaterialProcesVerbalCollection: IJTMaterialProcesVerbal[] = [sampleWithPartialData];
        expectedResult = service.addJTMaterialProcesVerbalToCollectionIfMissing(jTMaterialProcesVerbalCollection, jTMaterialProcesVerbal);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(jTMaterialProcesVerbal);
      });

      it('should add only unique JTMaterialProcesVerbal to an array', () => {
        const jTMaterialProcesVerbalArray: IJTMaterialProcesVerbal[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const jTMaterialProcesVerbalCollection: IJTMaterialProcesVerbal[] = [sampleWithRequiredData];
        expectedResult = service.addJTMaterialProcesVerbalToCollectionIfMissing(
          jTMaterialProcesVerbalCollection,
          ...jTMaterialProcesVerbalArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const jTMaterialProcesVerbal: IJTMaterialProcesVerbal = sampleWithRequiredData;
        const jTMaterialProcesVerbal2: IJTMaterialProcesVerbal = sampleWithPartialData;
        expectedResult = service.addJTMaterialProcesVerbalToCollectionIfMissing([], jTMaterialProcesVerbal, jTMaterialProcesVerbal2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(jTMaterialProcesVerbal);
        expect(expectedResult).toContain(jTMaterialProcesVerbal2);
      });

      it('should accept null and undefined values', () => {
        const jTMaterialProcesVerbal: IJTMaterialProcesVerbal = sampleWithRequiredData;
        expectedResult = service.addJTMaterialProcesVerbalToCollectionIfMissing([], null, jTMaterialProcesVerbal, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(jTMaterialProcesVerbal);
      });

      it('should return initial array if no JTMaterialProcesVerbal is added', () => {
        const jTMaterialProcesVerbalCollection: IJTMaterialProcesVerbal[] = [sampleWithRequiredData];
        expectedResult = service.addJTMaterialProcesVerbalToCollectionIfMissing(jTMaterialProcesVerbalCollection, undefined, null);
        expect(expectedResult).toEqual(jTMaterialProcesVerbalCollection);
      });
    });

    describe('compareJTMaterialProcesVerbal', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareJTMaterialProcesVerbal(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareJTMaterialProcesVerbal(entity1, entity2);
        const compareResult2 = service.compareJTMaterialProcesVerbal(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareJTMaterialProcesVerbal(entity1, entity2);
        const compareResult2 = service.compareJTMaterialProcesVerbal(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareJTMaterialProcesVerbal(entity1, entity2);
        const compareResult2 = service.compareJTMaterialProcesVerbal(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

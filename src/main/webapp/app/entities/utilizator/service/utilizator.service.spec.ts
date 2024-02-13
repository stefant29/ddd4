import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUtilizator } from '../utilizator.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../utilizator.test-samples';

import { UtilizatorService } from './utilizator.service';

const requireRestSample: IUtilizator = {
  ...sampleWithRequiredData,
};

describe('Utilizator Service', () => {
  let service: UtilizatorService;
  let httpMock: HttpTestingController;
  let expectedResult: IUtilizator | IUtilizator[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UtilizatorService);
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

    it('should create a Utilizator', () => {
      const utilizator = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(utilizator).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Utilizator', () => {
      const utilizator = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(utilizator).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Utilizator', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Utilizator', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Utilizator', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addUtilizatorToCollectionIfMissing', () => {
      it('should add a Utilizator to an empty array', () => {
        const utilizator: IUtilizator = sampleWithRequiredData;
        expectedResult = service.addUtilizatorToCollectionIfMissing([], utilizator);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(utilizator);
      });

      it('should not add a Utilizator to an array that contains it', () => {
        const utilizator: IUtilizator = sampleWithRequiredData;
        const utilizatorCollection: IUtilizator[] = [
          {
            ...utilizator,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addUtilizatorToCollectionIfMissing(utilizatorCollection, utilizator);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Utilizator to an array that doesn't contain it", () => {
        const utilizator: IUtilizator = sampleWithRequiredData;
        const utilizatorCollection: IUtilizator[] = [sampleWithPartialData];
        expectedResult = service.addUtilizatorToCollectionIfMissing(utilizatorCollection, utilizator);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(utilizator);
      });

      it('should add only unique Utilizator to an array', () => {
        const utilizatorArray: IUtilizator[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const utilizatorCollection: IUtilizator[] = [sampleWithRequiredData];
        expectedResult = service.addUtilizatorToCollectionIfMissing(utilizatorCollection, ...utilizatorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const utilizator: IUtilizator = sampleWithRequiredData;
        const utilizator2: IUtilizator = sampleWithPartialData;
        expectedResult = service.addUtilizatorToCollectionIfMissing([], utilizator, utilizator2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(utilizator);
        expect(expectedResult).toContain(utilizator2);
      });

      it('should accept null and undefined values', () => {
        const utilizator: IUtilizator = sampleWithRequiredData;
        expectedResult = service.addUtilizatorToCollectionIfMissing([], null, utilizator, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(utilizator);
      });

      it('should return initial array if no Utilizator is added', () => {
        const utilizatorCollection: IUtilizator[] = [sampleWithRequiredData];
        expectedResult = service.addUtilizatorToCollectionIfMissing(utilizatorCollection, undefined, null);
        expect(expectedResult).toEqual(utilizatorCollection);
      });
    });

    describe('compareUtilizator', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareUtilizator(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareUtilizator(entity1, entity2);
        const compareResult2 = service.compareUtilizator(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareUtilizator(entity1, entity2);
        const compareResult2 = service.compareUtilizator(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareUtilizator(entity1, entity2);
        const compareResult2 = service.compareUtilizator(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

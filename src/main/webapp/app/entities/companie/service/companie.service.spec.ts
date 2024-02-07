import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICompanie } from '../companie.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../companie.test-samples';

import { CompanieService } from './companie.service';

const requireRestSample: ICompanie = {
  ...sampleWithRequiredData,
};

describe('Companie Service', () => {
  let service: CompanieService;
  let httpMock: HttpTestingController;
  let expectedResult: ICompanie | ICompanie[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CompanieService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Companie', () => {
      const companie = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(companie).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Companie', () => {
      const companie = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(companie).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Companie', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Companie', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Companie', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCompanieToCollectionIfMissing', () => {
      it('should add a Companie to an empty array', () => {
        const companie: ICompanie = sampleWithRequiredData;
        expectedResult = service.addCompanieToCollectionIfMissing([], companie);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(companie);
      });

      it('should not add a Companie to an array that contains it', () => {
        const companie: ICompanie = sampleWithRequiredData;
        const companieCollection: ICompanie[] = [
          {
            ...companie,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCompanieToCollectionIfMissing(companieCollection, companie);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Companie to an array that doesn't contain it", () => {
        const companie: ICompanie = sampleWithRequiredData;
        const companieCollection: ICompanie[] = [sampleWithPartialData];
        expectedResult = service.addCompanieToCollectionIfMissing(companieCollection, companie);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(companie);
      });

      it('should add only unique Companie to an array', () => {
        const companieArray: ICompanie[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const companieCollection: ICompanie[] = [sampleWithRequiredData];
        expectedResult = service.addCompanieToCollectionIfMissing(companieCollection, ...companieArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const companie: ICompanie = sampleWithRequiredData;
        const companie2: ICompanie = sampleWithPartialData;
        expectedResult = service.addCompanieToCollectionIfMissing([], companie, companie2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(companie);
        expect(expectedResult).toContain(companie2);
      });

      it('should accept null and undefined values', () => {
        const companie: ICompanie = sampleWithRequiredData;
        expectedResult = service.addCompanieToCollectionIfMissing([], null, companie, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(companie);
      });

      it('should return initial array if no Companie is added', () => {
        const companieCollection: ICompanie[] = [sampleWithRequiredData];
        expectedResult = service.addCompanieToCollectionIfMissing(companieCollection, undefined, null);
        expect(expectedResult).toEqual(companieCollection);
      });
    });

    describe('compareCompanie', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCompanie(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCompanie(entity1, entity2);
        const compareResult2 = service.compareCompanie(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCompanie(entity1, entity2);
        const compareResult2 = service.compareCompanie(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCompanie(entity1, entity2);
        const compareResult2 = service.compareCompanie(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

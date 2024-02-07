import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IProcesVerbal } from '../proces-verbal.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../proces-verbal.test-samples';

import { ProcesVerbalService, RestProcesVerbal } from './proces-verbal.service';

const requireRestSample: RestProcesVerbal = {
  ...sampleWithRequiredData,
  data: sampleWithRequiredData.data?.format(DATE_FORMAT),
  ora: sampleWithRequiredData.ora?.toJSON(),
};

describe('ProcesVerbal Service', () => {
  let service: ProcesVerbalService;
  let httpMock: HttpTestingController;
  let expectedResult: IProcesVerbal | IProcesVerbal[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProcesVerbalService);
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

    it('should create a ProcesVerbal', () => {
      const procesVerbal = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(procesVerbal).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProcesVerbal', () => {
      const procesVerbal = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(procesVerbal).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProcesVerbal', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProcesVerbal', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProcesVerbal', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProcesVerbalToCollectionIfMissing', () => {
      it('should add a ProcesVerbal to an empty array', () => {
        const procesVerbal: IProcesVerbal = sampleWithRequiredData;
        expectedResult = service.addProcesVerbalToCollectionIfMissing([], procesVerbal);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(procesVerbal);
      });

      it('should not add a ProcesVerbal to an array that contains it', () => {
        const procesVerbal: IProcesVerbal = sampleWithRequiredData;
        const procesVerbalCollection: IProcesVerbal[] = [
          {
            ...procesVerbal,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProcesVerbalToCollectionIfMissing(procesVerbalCollection, procesVerbal);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProcesVerbal to an array that doesn't contain it", () => {
        const procesVerbal: IProcesVerbal = sampleWithRequiredData;
        const procesVerbalCollection: IProcesVerbal[] = [sampleWithPartialData];
        expectedResult = service.addProcesVerbalToCollectionIfMissing(procesVerbalCollection, procesVerbal);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(procesVerbal);
      });

      it('should add only unique ProcesVerbal to an array', () => {
        const procesVerbalArray: IProcesVerbal[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const procesVerbalCollection: IProcesVerbal[] = [sampleWithRequiredData];
        expectedResult = service.addProcesVerbalToCollectionIfMissing(procesVerbalCollection, ...procesVerbalArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const procesVerbal: IProcesVerbal = sampleWithRequiredData;
        const procesVerbal2: IProcesVerbal = sampleWithPartialData;
        expectedResult = service.addProcesVerbalToCollectionIfMissing([], procesVerbal, procesVerbal2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(procesVerbal);
        expect(expectedResult).toContain(procesVerbal2);
      });

      it('should accept null and undefined values', () => {
        const procesVerbal: IProcesVerbal = sampleWithRequiredData;
        expectedResult = service.addProcesVerbalToCollectionIfMissing([], null, procesVerbal, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(procesVerbal);
      });

      it('should return initial array if no ProcesVerbal is added', () => {
        const procesVerbalCollection: IProcesVerbal[] = [sampleWithRequiredData];
        expectedResult = service.addProcesVerbalToCollectionIfMissing(procesVerbalCollection, undefined, null);
        expect(expectedResult).toEqual(procesVerbalCollection);
      });
    });

    describe('compareProcesVerbal', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProcesVerbal(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareProcesVerbal(entity1, entity2);
        const compareResult2 = service.compareProcesVerbal(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareProcesVerbal(entity1, entity2);
        const compareResult2 = service.compareProcesVerbal(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareProcesVerbal(entity1, entity2);
        const compareResult2 = service.compareProcesVerbal(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

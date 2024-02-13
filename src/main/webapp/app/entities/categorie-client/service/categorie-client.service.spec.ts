import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICategorieClient } from '../categorie-client.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../categorie-client.test-samples';

import { CategorieClientService } from './categorie-client.service';

const requireRestSample: ICategorieClient = {
  ...sampleWithRequiredData,
};

describe('CategorieClient Service', () => {
  let service: CategorieClientService;
  let httpMock: HttpTestingController;
  let expectedResult: ICategorieClient | ICategorieClient[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CategorieClientService);
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

    it('should create a CategorieClient', () => {
      const categorieClient = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(categorieClient).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CategorieClient', () => {
      const categorieClient = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(categorieClient).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CategorieClient', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CategorieClient', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CategorieClient', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCategorieClientToCollectionIfMissing', () => {
      it('should add a CategorieClient to an empty array', () => {
        const categorieClient: ICategorieClient = sampleWithRequiredData;
        expectedResult = service.addCategorieClientToCollectionIfMissing([], categorieClient);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categorieClient);
      });

      it('should not add a CategorieClient to an array that contains it', () => {
        const categorieClient: ICategorieClient = sampleWithRequiredData;
        const categorieClientCollection: ICategorieClient[] = [
          {
            ...categorieClient,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCategorieClientToCollectionIfMissing(categorieClientCollection, categorieClient);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CategorieClient to an array that doesn't contain it", () => {
        const categorieClient: ICategorieClient = sampleWithRequiredData;
        const categorieClientCollection: ICategorieClient[] = [sampleWithPartialData];
        expectedResult = service.addCategorieClientToCollectionIfMissing(categorieClientCollection, categorieClient);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categorieClient);
      });

      it('should add only unique CategorieClient to an array', () => {
        const categorieClientArray: ICategorieClient[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const categorieClientCollection: ICategorieClient[] = [sampleWithRequiredData];
        expectedResult = service.addCategorieClientToCollectionIfMissing(categorieClientCollection, ...categorieClientArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const categorieClient: ICategorieClient = sampleWithRequiredData;
        const categorieClient2: ICategorieClient = sampleWithPartialData;
        expectedResult = service.addCategorieClientToCollectionIfMissing([], categorieClient, categorieClient2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categorieClient);
        expect(expectedResult).toContain(categorieClient2);
      });

      it('should accept null and undefined values', () => {
        const categorieClient: ICategorieClient = sampleWithRequiredData;
        expectedResult = service.addCategorieClientToCollectionIfMissing([], null, categorieClient, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categorieClient);
      });

      it('should return initial array if no CategorieClient is added', () => {
        const categorieClientCollection: ICategorieClient[] = [sampleWithRequiredData];
        expectedResult = service.addCategorieClientToCollectionIfMissing(categorieClientCollection, undefined, null);
        expect(expectedResult).toEqual(categorieClientCollection);
      });
    });

    describe('compareCategorieClient', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCategorieClient(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareCategorieClient(entity1, entity2);
        const compareResult2 = service.compareCategorieClient(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareCategorieClient(entity1, entity2);
        const compareResult2 = service.compareCategorieClient(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareCategorieClient(entity1, entity2);
        const compareResult2 = service.compareCategorieClient(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

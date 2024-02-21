import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IJTMaterialProcesVerbal } from '../jt-material-proces-verbal.model';
import { JTMaterialProcesVerbalService } from '../service/jt-material-proces-verbal.service';

import jTMaterialProcesVerbalResolve from './jt-material-proces-verbal-routing-resolve.service';

describe('JTMaterialProcesVerbal routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: JTMaterialProcesVerbalService;
  let resultJTMaterialProcesVerbal: IJTMaterialProcesVerbal | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(JTMaterialProcesVerbalService);
    resultJTMaterialProcesVerbal = undefined;
  });

  describe('resolve', () => {
    it('should return IJTMaterialProcesVerbal returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        jTMaterialProcesVerbalResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultJTMaterialProcesVerbal = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultJTMaterialProcesVerbal).toEqual({ id: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        jTMaterialProcesVerbalResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultJTMaterialProcesVerbal = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultJTMaterialProcesVerbal).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IJTMaterialProcesVerbal>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        jTMaterialProcesVerbalResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultJTMaterialProcesVerbal = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultJTMaterialProcesVerbal).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

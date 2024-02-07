import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICompanie } from 'app/entities/companie/companie.model';
import { CompanieService } from 'app/entities/companie/service/companie.service';
import { MaterialService } from '../service/material.service';
import { IMaterial } from '../material.model';
import { MaterialFormService } from './material-form.service';

import { MaterialUpdateComponent } from './material-update.component';

describe('Material Management Update Component', () => {
  let comp: MaterialUpdateComponent;
  let fixture: ComponentFixture<MaterialUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let materialFormService: MaterialFormService;
  let materialService: MaterialService;
  let companieService: CompanieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), MaterialUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(MaterialUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MaterialUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    materialFormService = TestBed.inject(MaterialFormService);
    materialService = TestBed.inject(MaterialService);
    companieService = TestBed.inject(CompanieService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Companie query and add missing value', () => {
      const material: IMaterial = { id: 'CBA' };
      const companie: ICompanie = { id: 10992 };
      material.companie = companie;

      const companieCollection: ICompanie[] = [{ id: 20393 }];
      jest.spyOn(companieService, 'query').mockReturnValue(of(new HttpResponse({ body: companieCollection })));
      const additionalCompanies = [companie];
      const expectedCollection: ICompanie[] = [...additionalCompanies, ...companieCollection];
      jest.spyOn(companieService, 'addCompanieToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ material });
      comp.ngOnInit();

      expect(companieService.query).toHaveBeenCalled();
      expect(companieService.addCompanieToCollectionIfMissing).toHaveBeenCalledWith(
        companieCollection,
        ...additionalCompanies.map(expect.objectContaining),
      );
      expect(comp.companiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const material: IMaterial = { id: 'CBA' };
      const companie: ICompanie = { id: 14373 };
      material.companie = companie;

      activatedRoute.data = of({ material });
      comp.ngOnInit();

      expect(comp.companiesSharedCollection).toContain(companie);
      expect(comp.material).toEqual(material);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMaterial>>();
      const material = { id: 'ABC' };
      jest.spyOn(materialFormService, 'getMaterial').mockReturnValue(material);
      jest.spyOn(materialService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ material });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: material }));
      saveSubject.complete();

      // THEN
      expect(materialFormService.getMaterial).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(materialService.update).toHaveBeenCalledWith(expect.objectContaining(material));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMaterial>>();
      const material = { id: 'ABC' };
      jest.spyOn(materialFormService, 'getMaterial').mockReturnValue({ id: null });
      jest.spyOn(materialService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ material: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: material }));
      saveSubject.complete();

      // THEN
      expect(materialFormService.getMaterial).toHaveBeenCalled();
      expect(materialService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMaterial>>();
      const material = { id: 'ABC' };
      jest.spyOn(materialService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ material });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(materialService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCompanie', () => {
      it('Should forward to companieService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(companieService, 'compareCompanie');
        comp.compareCompanie(entity, entity2);
        expect(companieService.compareCompanie).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

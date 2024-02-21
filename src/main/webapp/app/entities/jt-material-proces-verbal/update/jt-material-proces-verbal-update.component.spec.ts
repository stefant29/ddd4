import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IMaterial } from 'app/entities/material/material.model';
import { MaterialService } from 'app/entities/material/service/material.service';
import { IProcesVerbal } from 'app/entities/proces-verbal/proces-verbal.model';
import { ProcesVerbalService } from 'app/entities/proces-verbal/service/proces-verbal.service';
import { IJTMaterialProcesVerbal } from '../jt-material-proces-verbal.model';
import { JTMaterialProcesVerbalService } from '../service/jt-material-proces-verbal.service';
import { JTMaterialProcesVerbalFormService } from './jt-material-proces-verbal-form.service';

import { JTMaterialProcesVerbalUpdateComponent } from './jt-material-proces-verbal-update.component';

describe('JTMaterialProcesVerbal Management Update Component', () => {
  let comp: JTMaterialProcesVerbalUpdateComponent;
  let fixture: ComponentFixture<JTMaterialProcesVerbalUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let jTMaterialProcesVerbalFormService: JTMaterialProcesVerbalFormService;
  let jTMaterialProcesVerbalService: JTMaterialProcesVerbalService;
  let materialService: MaterialService;
  let procesVerbalService: ProcesVerbalService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), JTMaterialProcesVerbalUpdateComponent],
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
      .overrideTemplate(JTMaterialProcesVerbalUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(JTMaterialProcesVerbalUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    jTMaterialProcesVerbalFormService = TestBed.inject(JTMaterialProcesVerbalFormService);
    jTMaterialProcesVerbalService = TestBed.inject(JTMaterialProcesVerbalService);
    materialService = TestBed.inject(MaterialService);
    procesVerbalService = TestBed.inject(ProcesVerbalService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Material query and add missing value', () => {
      const jTMaterialProcesVerbal: IJTMaterialProcesVerbal = { id: 'CBA' };
      const produs: IMaterial = { id: 'd2589ca6-ca1f-4692-b03d-8ec7eadebad0' };
      jTMaterialProcesVerbal.produs = produs;

      const materialCollection: IMaterial[] = [{ id: '08d80b3c-3f63-4b0d-8029-6f39b06fd39f' }];
      jest.spyOn(materialService, 'query').mockReturnValue(of(new HttpResponse({ body: materialCollection })));
      const additionalMaterials = [produs];
      const expectedCollection: IMaterial[] = [...additionalMaterials, ...materialCollection];
      jest.spyOn(materialService, 'addMaterialToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ jTMaterialProcesVerbal });
      comp.ngOnInit();

      expect(materialService.query).toHaveBeenCalled();
      expect(materialService.addMaterialToCollectionIfMissing).toHaveBeenCalledWith(
        materialCollection,
        ...additionalMaterials.map(expect.objectContaining),
      );
      expect(comp.materialsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ProcesVerbal query and add missing value', () => {
      const jTMaterialProcesVerbal: IJTMaterialProcesVerbal = { id: 'CBA' };
      const procesVerbal: IProcesVerbal = { id: '7f5ad808-b96d-49d1-a63e-0cd44eb47c9a' };
      jTMaterialProcesVerbal.procesVerbal = procesVerbal;

      const procesVerbalCollection: IProcesVerbal[] = [{ id: '8c50fee4-3e8d-4d6e-b38d-27ce6931b22e' }];
      jest.spyOn(procesVerbalService, 'query').mockReturnValue(of(new HttpResponse({ body: procesVerbalCollection })));
      const additionalProcesVerbals = [procesVerbal];
      const expectedCollection: IProcesVerbal[] = [...additionalProcesVerbals, ...procesVerbalCollection];
      jest.spyOn(procesVerbalService, 'addProcesVerbalToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ jTMaterialProcesVerbal });
      comp.ngOnInit();

      expect(procesVerbalService.query).toHaveBeenCalled();
      expect(procesVerbalService.addProcesVerbalToCollectionIfMissing).toHaveBeenCalledWith(
        procesVerbalCollection,
        ...additionalProcesVerbals.map(expect.objectContaining),
      );
      expect(comp.procesVerbalsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const jTMaterialProcesVerbal: IJTMaterialProcesVerbal = { id: 'CBA' };
      const produs: IMaterial = { id: '34238fcb-49b4-4ef9-b609-a5adf4c0b686' };
      jTMaterialProcesVerbal.produs = produs;
      const procesVerbal: IProcesVerbal = { id: 'dac91299-869b-44e3-9a65-9536181845c9' };
      jTMaterialProcesVerbal.procesVerbal = procesVerbal;

      activatedRoute.data = of({ jTMaterialProcesVerbal });
      comp.ngOnInit();

      expect(comp.materialsSharedCollection).toContain(produs);
      expect(comp.procesVerbalsSharedCollection).toContain(procesVerbal);
      expect(comp.jTMaterialProcesVerbal).toEqual(jTMaterialProcesVerbal);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJTMaterialProcesVerbal>>();
      const jTMaterialProcesVerbal = { id: 'ABC' };
      jest.spyOn(jTMaterialProcesVerbalFormService, 'getJTMaterialProcesVerbal').mockReturnValue(jTMaterialProcesVerbal);
      jest.spyOn(jTMaterialProcesVerbalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jTMaterialProcesVerbal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: jTMaterialProcesVerbal }));
      saveSubject.complete();

      // THEN
      expect(jTMaterialProcesVerbalFormService.getJTMaterialProcesVerbal).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(jTMaterialProcesVerbalService.update).toHaveBeenCalledWith(expect.objectContaining(jTMaterialProcesVerbal));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJTMaterialProcesVerbal>>();
      const jTMaterialProcesVerbal = { id: 'ABC' };
      jest.spyOn(jTMaterialProcesVerbalFormService, 'getJTMaterialProcesVerbal').mockReturnValue({ id: null });
      jest.spyOn(jTMaterialProcesVerbalService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jTMaterialProcesVerbal: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: jTMaterialProcesVerbal }));
      saveSubject.complete();

      // THEN
      expect(jTMaterialProcesVerbalFormService.getJTMaterialProcesVerbal).toHaveBeenCalled();
      expect(jTMaterialProcesVerbalService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJTMaterialProcesVerbal>>();
      const jTMaterialProcesVerbal = { id: 'ABC' };
      jest.spyOn(jTMaterialProcesVerbalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jTMaterialProcesVerbal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(jTMaterialProcesVerbalService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMaterial', () => {
      it('Should forward to materialService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(materialService, 'compareMaterial');
        comp.compareMaterial(entity, entity2);
        expect(materialService.compareMaterial).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProcesVerbal', () => {
      it('Should forward to procesVerbalService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(procesVerbalService, 'compareProcesVerbal');
        comp.compareProcesVerbal(entity, entity2);
        expect(procesVerbalService.compareProcesVerbal).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICompanie } from 'app/entities/companie/companie.model';
import { CompanieService } from 'app/entities/companie/service/companie.service';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';
import { IUtilizator } from 'app/entities/utilizator/utilizator.model';
import { UtilizatorService } from 'app/entities/utilizator/service/utilizator.service';
import { IProcesVerbal } from '../proces-verbal.model';
import { ProcesVerbalService } from '../service/proces-verbal.service';
import { ProcesVerbalFormService } from './proces-verbal-form.service';

import { ProcesVerbalUpdateComponent } from './proces-verbal-update.component';

describe('ProcesVerbal Management Update Component', () => {
  let comp: ProcesVerbalUpdateComponent;
  let fixture: ComponentFixture<ProcesVerbalUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let procesVerbalFormService: ProcesVerbalFormService;
  let procesVerbalService: ProcesVerbalService;
  let companieService: CompanieService;
  let clientService: ClientService;
  let utilizatorService: UtilizatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ProcesVerbalUpdateComponent],
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
      .overrideTemplate(ProcesVerbalUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProcesVerbalUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    procesVerbalFormService = TestBed.inject(ProcesVerbalFormService);
    procesVerbalService = TestBed.inject(ProcesVerbalService);
    companieService = TestBed.inject(CompanieService);
    clientService = TestBed.inject(ClientService);
    utilizatorService = TestBed.inject(UtilizatorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Companie query and add missing value', () => {
      const procesVerbal: IProcesVerbal = { id: 'CBA' };
      const companie: ICompanie = { id: '6e5938c0-7f8b-418b-921c-4296b2bd814c' };
      procesVerbal.companie = companie;

      const companieCollection: ICompanie[] = [{ id: 'd4a44b68-d9a6-4ef3-ae95-d94b328cf71a' }];
      jest.spyOn(companieService, 'query').mockReturnValue(of(new HttpResponse({ body: companieCollection })));
      const additionalCompanies = [companie];
      const expectedCollection: ICompanie[] = [...additionalCompanies, ...companieCollection];
      jest.spyOn(companieService, 'addCompanieToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ procesVerbal });
      comp.ngOnInit();

      expect(companieService.query).toHaveBeenCalled();
      expect(companieService.addCompanieToCollectionIfMissing).toHaveBeenCalledWith(
        companieCollection,
        ...additionalCompanies.map(expect.objectContaining),
      );
      expect(comp.companiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Client query and add missing value', () => {
      const procesVerbal: IProcesVerbal = { id: 'CBA' };
      const client: IClient = { id: '1371ffeb-f0f2-4b3c-aee9-3d5f8da99d25' };
      procesVerbal.client = client;

      const clientCollection: IClient[] = [{ id: '83becd7a-755e-45c4-aad6-939eeb3c3e22' }];
      jest.spyOn(clientService, 'query').mockReturnValue(of(new HttpResponse({ body: clientCollection })));
      const additionalClients = [client];
      const expectedCollection: IClient[] = [...additionalClients, ...clientCollection];
      jest.spyOn(clientService, 'addClientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ procesVerbal });
      comp.ngOnInit();

      expect(clientService.query).toHaveBeenCalled();
      expect(clientService.addClientToCollectionIfMissing).toHaveBeenCalledWith(
        clientCollection,
        ...additionalClients.map(expect.objectContaining),
      );
      expect(comp.clientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Utilizator query and add missing value', () => {
      const procesVerbal: IProcesVerbal = { id: 'CBA' };
      const operator: IUtilizator = { id: 'c5abea24-def6-4d54-82ff-6706c9725775' };
      procesVerbal.operator = operator;

      const utilizatorCollection: IUtilizator[] = [{ id: '20dae6a1-2f82-4c82-b2a2-4ab835dbb52e' }];
      jest.spyOn(utilizatorService, 'query').mockReturnValue(of(new HttpResponse({ body: utilizatorCollection })));
      const additionalUtilizators = [operator];
      const expectedCollection: IUtilizator[] = [...additionalUtilizators, ...utilizatorCollection];
      jest.spyOn(utilizatorService, 'addUtilizatorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ procesVerbal });
      comp.ngOnInit();

      expect(utilizatorService.query).toHaveBeenCalled();
      expect(utilizatorService.addUtilizatorToCollectionIfMissing).toHaveBeenCalledWith(
        utilizatorCollection,
        ...additionalUtilizators.map(expect.objectContaining),
      );
      expect(comp.utilizatorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const procesVerbal: IProcesVerbal = { id: 'CBA' };
      const companie: ICompanie = { id: '22c4a8d5-249e-4968-9292-39937c881d16' };
      procesVerbal.companie = companie;
      const client: IClient = { id: '656936bd-eb7b-40ed-b1ee-acdfb5b76f03' };
      procesVerbal.client = client;
      const operator: IUtilizator = { id: 'e6fac43f-935f-49ee-9551-864915479bab' };
      procesVerbal.operator = operator;

      activatedRoute.data = of({ procesVerbal });
      comp.ngOnInit();

      expect(comp.companiesSharedCollection).toContain(companie);
      expect(comp.clientsSharedCollection).toContain(client);
      expect(comp.utilizatorsSharedCollection).toContain(operator);
      expect(comp.procesVerbal).toEqual(procesVerbal);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProcesVerbal>>();
      const procesVerbal = { id: 'ABC' };
      jest.spyOn(procesVerbalFormService, 'getProcesVerbal').mockReturnValue(procesVerbal);
      jest.spyOn(procesVerbalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ procesVerbal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: procesVerbal }));
      saveSubject.complete();

      // THEN
      expect(procesVerbalFormService.getProcesVerbal).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(procesVerbalService.update).toHaveBeenCalledWith(expect.objectContaining(procesVerbal));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProcesVerbal>>();
      const procesVerbal = { id: 'ABC' };
      jest.spyOn(procesVerbalFormService, 'getProcesVerbal').mockReturnValue({ id: null });
      jest.spyOn(procesVerbalService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ procesVerbal: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: procesVerbal }));
      saveSubject.complete();

      // THEN
      expect(procesVerbalFormService.getProcesVerbal).toHaveBeenCalled();
      expect(procesVerbalService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProcesVerbal>>();
      const procesVerbal = { id: 'ABC' };
      jest.spyOn(procesVerbalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ procesVerbal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(procesVerbalService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCompanie', () => {
      it('Should forward to companieService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(companieService, 'compareCompanie');
        comp.compareCompanie(entity, entity2);
        expect(companieService.compareCompanie).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareClient', () => {
      it('Should forward to clientService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(clientService, 'compareClient');
        comp.compareClient(entity, entity2);
        expect(clientService.compareClient).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareUtilizator', () => {
      it('Should forward to utilizatorService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(utilizatorService, 'compareUtilizator');
        comp.compareUtilizator(entity, entity2);
        expect(utilizatorService.compareUtilizator).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

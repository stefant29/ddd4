import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICompanie } from 'app/entities/companie/companie.model';
import { CompanieService } from 'app/entities/companie/service/companie.service';
import { ICategorieClient } from 'app/entities/categorie-client/categorie-client.model';
import { CategorieClientService } from 'app/entities/categorie-client/service/categorie-client.service';
import { IClient } from '../client.model';
import { ClientService } from '../service/client.service';
import { ClientFormService } from './client-form.service';

import { ClientUpdateComponent } from './client-update.component';

describe('Client Management Update Component', () => {
  let comp: ClientUpdateComponent;
  let fixture: ComponentFixture<ClientUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let clientFormService: ClientFormService;
  let clientService: ClientService;
  let companieService: CompanieService;
  let categorieClientService: CategorieClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ClientUpdateComponent],
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
      .overrideTemplate(ClientUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ClientUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    clientFormService = TestBed.inject(ClientFormService);
    clientService = TestBed.inject(ClientService);
    companieService = TestBed.inject(CompanieService);
    categorieClientService = TestBed.inject(CategorieClientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Companie query and add missing value', () => {
      const client: IClient = { id: 'CBA' };
      const companie: ICompanie = { id: '2ddf1b26-0409-488f-805b-8127760deba0' };
      client.companie = companie;

      const companieCollection: ICompanie[] = [{ id: '0cf29935-8636-4726-962b-96d62e2c390a' }];
      jest.spyOn(companieService, 'query').mockReturnValue(of(new HttpResponse({ body: companieCollection })));
      const additionalCompanies = [companie];
      const expectedCollection: ICompanie[] = [...additionalCompanies, ...companieCollection];
      jest.spyOn(companieService, 'addCompanieToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ client });
      comp.ngOnInit();

      expect(companieService.query).toHaveBeenCalled();
      expect(companieService.addCompanieToCollectionIfMissing).toHaveBeenCalledWith(
        companieCollection,
        ...additionalCompanies.map(expect.objectContaining),
      );
      expect(comp.companiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CategorieClient query and add missing value', () => {
      const client: IClient = { id: 'CBA' };
      const categorie: ICategorieClient = { id: 'f6cd0600-513f-4f62-a71f-1a272264df63' };
      client.categorie = categorie;

      const categorieClientCollection: ICategorieClient[] = [{ id: '41b23ac2-b0a3-4815-8784-a3efeec204ab' }];
      jest.spyOn(categorieClientService, 'query').mockReturnValue(of(new HttpResponse({ body: categorieClientCollection })));
      const additionalCategorieClients = [categorie];
      const expectedCollection: ICategorieClient[] = [...additionalCategorieClients, ...categorieClientCollection];
      jest.spyOn(categorieClientService, 'addCategorieClientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ client });
      comp.ngOnInit();

      expect(categorieClientService.query).toHaveBeenCalled();
      expect(categorieClientService.addCategorieClientToCollectionIfMissing).toHaveBeenCalledWith(
        categorieClientCollection,
        ...additionalCategorieClients.map(expect.objectContaining),
      );
      expect(comp.categorieClientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const client: IClient = { id: 'CBA' };
      const companie: ICompanie = { id: 'b2ddf114-b004-47e8-a053-2a9a667841a5' };
      client.companie = companie;
      const categorie: ICategorieClient = { id: '6fe38f2e-b313-49b0-8ae3-0c28dfb69c93' };
      client.categorie = categorie;

      activatedRoute.data = of({ client });
      comp.ngOnInit();

      expect(comp.companiesSharedCollection).toContain(companie);
      expect(comp.categorieClientsSharedCollection).toContain(categorie);
      expect(comp.client).toEqual(client);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClient>>();
      const client = { id: 'ABC' };
      jest.spyOn(clientFormService, 'getClient').mockReturnValue(client);
      jest.spyOn(clientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ client });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: client }));
      saveSubject.complete();

      // THEN
      expect(clientFormService.getClient).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(clientService.update).toHaveBeenCalledWith(expect.objectContaining(client));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClient>>();
      const client = { id: 'ABC' };
      jest.spyOn(clientFormService, 'getClient').mockReturnValue({ id: null });
      jest.spyOn(clientService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ client: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: client }));
      saveSubject.complete();

      // THEN
      expect(clientFormService.getClient).toHaveBeenCalled();
      expect(clientService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClient>>();
      const client = { id: 'ABC' };
      jest.spyOn(clientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ client });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(clientService.update).toHaveBeenCalled();
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

    describe('compareCategorieClient', () => {
      it('Should forward to categorieClientService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(categorieClientService, 'compareCategorieClient');
        comp.compareCategorieClient(entity, entity2);
        expect(categorieClientService.compareCategorieClient).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

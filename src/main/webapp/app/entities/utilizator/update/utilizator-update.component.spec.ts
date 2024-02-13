import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ICompanie } from 'app/entities/companie/companie.model';
import { CompanieService } from 'app/entities/companie/service/companie.service';
import { IUtilizator } from '../utilizator.model';
import { UtilizatorService } from '../service/utilizator.service';
import { UtilizatorFormService } from './utilizator-form.service';

import { UtilizatorUpdateComponent } from './utilizator-update.component';

describe('Utilizator Management Update Component', () => {
  let comp: UtilizatorUpdateComponent;
  let fixture: ComponentFixture<UtilizatorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let utilizatorFormService: UtilizatorFormService;
  let utilizatorService: UtilizatorService;
  let userService: UserService;
  let companieService: CompanieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), UtilizatorUpdateComponent],
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
      .overrideTemplate(UtilizatorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UtilizatorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    utilizatorFormService = TestBed.inject(UtilizatorFormService);
    utilizatorService = TestBed.inject(UtilizatorService);
    userService = TestBed.inject(UserService);
    companieService = TestBed.inject(CompanieService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const utilizator: IUtilizator = { id: 'CBA' };
      const user: IUser = { id: 11355 };
      utilizator.user = user;

      const userCollection: IUser[] = [{ id: 29446 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ utilizator });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining),
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Companie query and add missing value', () => {
      const utilizator: IUtilizator = { id: 'CBA' };
      const companie: ICompanie = { id: 'cb06e0fe-2764-4b02-a881-79aa66a8ded9' };
      utilizator.companie = companie;

      const companieCollection: ICompanie[] = [{ id: 'cf5b863f-8972-4c5a-a379-a79f493369b4' }];
      jest.spyOn(companieService, 'query').mockReturnValue(of(new HttpResponse({ body: companieCollection })));
      const additionalCompanies = [companie];
      const expectedCollection: ICompanie[] = [...additionalCompanies, ...companieCollection];
      jest.spyOn(companieService, 'addCompanieToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ utilizator });
      comp.ngOnInit();

      expect(companieService.query).toHaveBeenCalled();
      expect(companieService.addCompanieToCollectionIfMissing).toHaveBeenCalledWith(
        companieCollection,
        ...additionalCompanies.map(expect.objectContaining),
      );
      expect(comp.companiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const utilizator: IUtilizator = { id: 'CBA' };
      const user: IUser = { id: 25513 };
      utilizator.user = user;
      const companie: ICompanie = { id: '376204df-08b2-4e28-90bd-21fc36229086' };
      utilizator.companie = companie;

      activatedRoute.data = of({ utilizator });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.companiesSharedCollection).toContain(companie);
      expect(comp.utilizator).toEqual(utilizator);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUtilizator>>();
      const utilizator = { id: 'ABC' };
      jest.spyOn(utilizatorFormService, 'getUtilizator').mockReturnValue(utilizator);
      jest.spyOn(utilizatorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ utilizator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: utilizator }));
      saveSubject.complete();

      // THEN
      expect(utilizatorFormService.getUtilizator).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(utilizatorService.update).toHaveBeenCalledWith(expect.objectContaining(utilizator));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUtilizator>>();
      const utilizator = { id: 'ABC' };
      jest.spyOn(utilizatorFormService, 'getUtilizator').mockReturnValue({ id: null });
      jest.spyOn(utilizatorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ utilizator: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: utilizator }));
      saveSubject.complete();

      // THEN
      expect(utilizatorFormService.getUtilizator).toHaveBeenCalled();
      expect(utilizatorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUtilizator>>();
      const utilizator = { id: 'ABC' };
      jest.spyOn(utilizatorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ utilizator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(utilizatorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCompanie', () => {
      it('Should forward to companieService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(companieService, 'compareCompanie');
        comp.compareCompanie(entity, entity2);
        expect(companieService.compareCompanie).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

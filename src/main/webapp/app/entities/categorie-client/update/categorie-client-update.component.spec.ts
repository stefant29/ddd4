import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CategorieClientService } from '../service/categorie-client.service';
import { ICategorieClient } from '../categorie-client.model';
import { CategorieClientFormService } from './categorie-client-form.service';

import { CategorieClientUpdateComponent } from './categorie-client-update.component';

describe('CategorieClient Management Update Component', () => {
  let comp: CategorieClientUpdateComponent;
  let fixture: ComponentFixture<CategorieClientUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let categorieClientFormService: CategorieClientFormService;
  let categorieClientService: CategorieClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CategorieClientUpdateComponent],
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
      .overrideTemplate(CategorieClientUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CategorieClientUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    categorieClientFormService = TestBed.inject(CategorieClientFormService);
    categorieClientService = TestBed.inject(CategorieClientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const categorieClient: ICategorieClient = { id: 'CBA' };

      activatedRoute.data = of({ categorieClient });
      comp.ngOnInit();

      expect(comp.categorieClient).toEqual(categorieClient);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorieClient>>();
      const categorieClient = { id: 'ABC' };
      jest.spyOn(categorieClientFormService, 'getCategorieClient').mockReturnValue(categorieClient);
      jest.spyOn(categorieClientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categorieClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categorieClient }));
      saveSubject.complete();

      // THEN
      expect(categorieClientFormService.getCategorieClient).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(categorieClientService.update).toHaveBeenCalledWith(expect.objectContaining(categorieClient));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorieClient>>();
      const categorieClient = { id: 'ABC' };
      jest.spyOn(categorieClientFormService, 'getCategorieClient').mockReturnValue({ id: null });
      jest.spyOn(categorieClientService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categorieClient: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categorieClient }));
      saveSubject.complete();

      // THEN
      expect(categorieClientFormService.getCategorieClient).toHaveBeenCalled();
      expect(categorieClientService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorieClient>>();
      const categorieClient = { id: 'ABC' };
      jest.spyOn(categorieClientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categorieClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(categorieClientService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

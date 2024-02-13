import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CompanieService } from '../service/companie.service';
import { ICompanie } from '../companie.model';
import { CompanieFormService } from './companie-form.service';

import { CompanieUpdateComponent } from './companie-update.component';

describe('Companie Management Update Component', () => {
  let comp: CompanieUpdateComponent;
  let fixture: ComponentFixture<CompanieUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let companieFormService: CompanieFormService;
  let companieService: CompanieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CompanieUpdateComponent],
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
      .overrideTemplate(CompanieUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CompanieUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    companieFormService = TestBed.inject(CompanieFormService);
    companieService = TestBed.inject(CompanieService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const companie: ICompanie = { id: 'CBA' };

      activatedRoute.data = of({ companie });
      comp.ngOnInit();

      expect(comp.companie).toEqual(companie);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompanie>>();
      const companie = { id: 'ABC' };
      jest.spyOn(companieFormService, 'getCompanie').mockReturnValue(companie);
      jest.spyOn(companieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: companie }));
      saveSubject.complete();

      // THEN
      expect(companieFormService.getCompanie).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(companieService.update).toHaveBeenCalledWith(expect.objectContaining(companie));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompanie>>();
      const companie = { id: 'ABC' };
      jest.spyOn(companieFormService, 'getCompanie').mockReturnValue({ id: null });
      jest.spyOn(companieService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companie: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: companie }));
      saveSubject.complete();

      // THEN
      expect(companieFormService.getCompanie).toHaveBeenCalled();
      expect(companieService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompanie>>();
      const companie = { id: 'ABC' };
      jest.spyOn(companieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(companieService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

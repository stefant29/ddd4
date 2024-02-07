import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProcesVerbalService } from '../service/proces-verbal.service';

import { ProcesVerbalComponent } from './proces-verbal.component';

describe('ProcesVerbal Management Component', () => {
  let comp: ProcesVerbalComponent;
  let fixture: ComponentFixture<ProcesVerbalComponent>;
  let service: ProcesVerbalService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'proces-verbal', component: ProcesVerbalComponent }]),
        HttpClientTestingModule,
        ProcesVerbalComponent,
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              }),
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(ProcesVerbalComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProcesVerbalComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ProcesVerbalService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 'ABC' }],
          headers,
        }),
      ),
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.procesVerbals?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to procesVerbalService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getProcesVerbalIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getProcesVerbalIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CompanieService } from '../service/companie.service';

import { CompanieComponent } from './companie.component';

describe('Companie Management Component', () => {
  let comp: CompanieComponent;
  let fixture: ComponentFixture<CompanieComponent>;
  let service: CompanieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'companie', component: CompanieComponent }]),
        HttpClientTestingModule,
        CompanieComponent,
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
      .overrideTemplate(CompanieComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CompanieComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CompanieService);

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
    expect(comp.companies?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to companieService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getCompanieIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getCompanieIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});

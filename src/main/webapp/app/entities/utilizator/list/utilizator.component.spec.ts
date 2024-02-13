import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { UtilizatorService } from '../service/utilizator.service';

import { UtilizatorComponent } from './utilizator.component';

describe('Utilizator Management Component', () => {
  let comp: UtilizatorComponent;
  let fixture: ComponentFixture<UtilizatorComponent>;
  let service: UtilizatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'utilizator', component: UtilizatorComponent }]),
        HttpClientTestingModule,
        UtilizatorComponent,
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
      .overrideTemplate(UtilizatorComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UtilizatorComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(UtilizatorService);

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
    expect(comp.utilizators?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to utilizatorService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getUtilizatorIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getUtilizatorIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { JTMaterialProcesVerbalService } from '../service/jt-material-proces-verbal.service';

import { JTMaterialProcesVerbalComponent } from './jt-material-proces-verbal.component';

describe('JTMaterialProcesVerbal Management Component', () => {
  let comp: JTMaterialProcesVerbalComponent;
  let fixture: ComponentFixture<JTMaterialProcesVerbalComponent>;
  let service: JTMaterialProcesVerbalService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'jt-material-proces-verbal', component: JTMaterialProcesVerbalComponent }]),
        HttpClientTestingModule,
        JTMaterialProcesVerbalComponent,
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
      .overrideTemplate(JTMaterialProcesVerbalComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(JTMaterialProcesVerbalComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(JTMaterialProcesVerbalService);

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
    expect(comp.jTMaterialProcesVerbals?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to jTMaterialProcesVerbalService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getJTMaterialProcesVerbalIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getJTMaterialProcesVerbalIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});

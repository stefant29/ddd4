import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MaterialService } from '../service/material.service';

import { MaterialComponent } from './material.component';

describe('Material Management Component', () => {
  let comp: MaterialComponent;
  let fixture: ComponentFixture<MaterialComponent>;
  let service: MaterialService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'material', component: MaterialComponent }]),
        HttpClientTestingModule,
        MaterialComponent,
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
      .overrideTemplate(MaterialComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MaterialComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MaterialService);

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
    expect(comp.materials?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to materialService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getMaterialIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getMaterialIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});

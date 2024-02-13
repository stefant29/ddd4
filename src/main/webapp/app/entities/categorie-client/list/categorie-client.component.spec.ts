import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CategorieClientService } from '../service/categorie-client.service';

import { CategorieClientComponent } from './categorie-client.component';

describe('CategorieClient Management Component', () => {
  let comp: CategorieClientComponent;
  let fixture: ComponentFixture<CategorieClientComponent>;
  let service: CategorieClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'categorie-client', component: CategorieClientComponent }]),
        HttpClientTestingModule,
        CategorieClientComponent,
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
      .overrideTemplate(CategorieClientComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CategorieClientComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CategorieClientService);

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
    expect(comp.categorieClients?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to categorieClientService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getCategorieClientIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getCategorieClientIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});

import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CategorieClientDetailComponent } from './categorie-client-detail.component';

describe('CategorieClient Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategorieClientDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CategorieClientDetailComponent,
              resolve: { categorieClient: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CategorieClientDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load categorieClient on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CategorieClientDetailComponent);

      // THEN
      expect(instance.categorieClient).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});

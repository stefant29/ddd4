import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CompanieDetailComponent } from './companie-detail.component';

describe('Companie Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompanieDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CompanieDetailComponent,
              resolve: { companie: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CompanieDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load companie on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CompanieDetailComponent);

      // THEN
      expect(instance.companie).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});

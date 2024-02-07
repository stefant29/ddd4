import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { UtilizatorDetailComponent } from './utilizator-detail.component';

describe('Utilizator Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UtilizatorDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: UtilizatorDetailComponent,
              resolve: { utilizator: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(UtilizatorDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load utilizator on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', UtilizatorDetailComponent);

      // THEN
      expect(instance.utilizator).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});

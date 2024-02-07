import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProcesVerbalDetailComponent } from './proces-verbal-detail.component';

describe('ProcesVerbal Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProcesVerbalDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ProcesVerbalDetailComponent,
              resolve: { procesVerbal: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ProcesVerbalDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load procesVerbal on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ProcesVerbalDetailComponent);

      // THEN
      expect(instance.procesVerbal).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});

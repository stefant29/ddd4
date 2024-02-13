import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { JTMaterialProcesVerbalDetailComponent } from './jt-material-proces-verbal-detail.component';

describe('JTMaterialProcesVerbal Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JTMaterialProcesVerbalDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: JTMaterialProcesVerbalDetailComponent,
              resolve: { jTMaterialProcesVerbal: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(JTMaterialProcesVerbalDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load jTMaterialProcesVerbal on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', JTMaterialProcesVerbalDetailComponent);

      // THEN
      expect(instance.jTMaterialProcesVerbal).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});

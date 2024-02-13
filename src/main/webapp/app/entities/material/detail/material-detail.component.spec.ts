import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MaterialDetailComponent } from './material-detail.component';

describe('Material Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MaterialDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: MaterialDetailComponent,
              resolve: { material: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MaterialDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load material on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MaterialDetailComponent);

      // THEN
      expect(instance.material).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});

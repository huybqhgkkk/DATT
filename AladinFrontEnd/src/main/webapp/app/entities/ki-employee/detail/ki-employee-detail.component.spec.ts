import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KiEmployeeDetailComponent } from './ki-employee-detail.component';

describe('Component Tests', () => {
  describe('KiEmployee Management Detail Component', () => {
    let comp: KiEmployeeDetailComponent;
    let fixture: ComponentFixture<KiEmployeeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [KiEmployeeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ kiEmployee: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(KiEmployeeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(KiEmployeeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load kiEmployee on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.kiEmployee).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});

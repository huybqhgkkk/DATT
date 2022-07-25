import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Heroes3DetailComponent } from './heroes-3-detail.component';

describe('Component Tests', () => {
  describe('Heroes3 Management Detail Component', () => {
    let comp: Heroes3DetailComponent;
    let fixture: ComponentFixture<Heroes3DetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [Heroes3DetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ heroes3: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(Heroes3DetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Heroes3DetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load heroes3 on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.heroes3).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

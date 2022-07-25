import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Heroes2DetailComponent } from './heroes-2-detail.component';

describe('Component Tests', () => {
  describe('Heroes2 Management Detail Component', () => {
    let comp: Heroes2DetailComponent;
    let fixture: ComponentFixture<Heroes2DetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [Heroes2DetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ heroes2: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(Heroes2DetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Heroes2DetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load heroes2 on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.heroes2).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

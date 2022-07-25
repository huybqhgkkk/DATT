import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HeroesDetailComponent } from './heroes-detail.component';

describe('Component Tests', () => {
  describe('Heroes Management Detail Component', () => {
    let comp: HeroesDetailComponent;
    let fixture: ComponentFixture<HeroesDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [HeroesDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ heroes: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(HeroesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HeroesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load heroes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.heroes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

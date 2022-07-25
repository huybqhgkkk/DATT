import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RecruitmenDetailComponent } from './recruitmen-detail.component';

describe('Component Tests', () => {
  describe('Recruitmen Management Detail Component', () => {
    let comp: RecruitmenDetailComponent;
    let fixture: ComponentFixture<RecruitmenDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [RecruitmenDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ recruitmen: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(RecruitmenDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecruitmenDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load recruitmen on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.recruitmen).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});

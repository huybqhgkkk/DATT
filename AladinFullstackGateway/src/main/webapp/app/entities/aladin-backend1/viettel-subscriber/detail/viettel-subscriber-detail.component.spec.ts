import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ViettelSubscriberDetailComponent } from './viettel-subscriber-detail.component';

describe('Component Tests', () => {
  describe('ViettelSubscriber Management Detail Component', () => {
    let comp: ViettelSubscriberDetailComponent;
    let fixture: ComponentFixture<ViettelSubscriberDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ViettelSubscriberDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ viettelSubscriber: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ViettelSubscriberDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ViettelSubscriberDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load viettelSubscriber on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.viettelSubscriber).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

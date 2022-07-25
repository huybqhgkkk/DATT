import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ViettelSubscriber2DetailComponent } from './viettel-subscriber-2-detail.component';

describe('Component Tests', () => {
  describe('ViettelSubscriber2 Management Detail Component', () => {
    let comp: ViettelSubscriber2DetailComponent;
    let fixture: ComponentFixture<ViettelSubscriber2DetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ViettelSubscriber2DetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ viettelSubscriber2: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ViettelSubscriber2DetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ViettelSubscriber2DetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load viettelSubscriber2 on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.viettelSubscriber2).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ViettelSubscriberService } from '../service/viettel-subscriber.service';
import { IViettelSubscriber, ViettelSubscriber } from '../viettel-subscriber.model';

import { ViettelSubscriberUpdateComponent } from './viettel-subscriber-update.component';

describe('Component Tests', () => {
  describe('ViettelSubscriber Management Update Component', () => {
    let comp: ViettelSubscriberUpdateComponent;
    let fixture: ComponentFixture<ViettelSubscriberUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let viettelSubscriberService: ViettelSubscriberService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ViettelSubscriberUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ViettelSubscriberUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ViettelSubscriberUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      viettelSubscriberService = TestBed.inject(ViettelSubscriberService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const viettelSubscriber: IViettelSubscriber = { id: 456 };

        activatedRoute.data = of({ viettelSubscriber });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(viettelSubscriber));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const viettelSubscriber = { id: 123 };
        spyOn(viettelSubscriberService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ viettelSubscriber });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: viettelSubscriber }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(viettelSubscriberService.update).toHaveBeenCalledWith(viettelSubscriber);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const viettelSubscriber = new ViettelSubscriber();
        spyOn(viettelSubscriberService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ viettelSubscriber });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: viettelSubscriber }));
        saveSubject.complete();

        // THEN
        expect(viettelSubscriberService.create).toHaveBeenCalledWith(viettelSubscriber);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const viettelSubscriber = { id: 123 };
        spyOn(viettelSubscriberService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ viettelSubscriber });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(viettelSubscriberService.update).toHaveBeenCalledWith(viettelSubscriber);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});

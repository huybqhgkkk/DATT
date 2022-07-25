jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ViettelSubscriber2Service } from '../service/viettel-subscriber-2.service';
import { IViettelSubscriber2, ViettelSubscriber2 } from '../viettel-subscriber-2.model';

import { ViettelSubscriber2UpdateComponent } from './viettel-subscriber-2-update.component';

describe('Component Tests', () => {
  describe('ViettelSubscriber2 Management Update Component', () => {
    let comp: ViettelSubscriber2UpdateComponent;
    let fixture: ComponentFixture<ViettelSubscriber2UpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let viettelSubscriber2Service: ViettelSubscriber2Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ViettelSubscriber2UpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ViettelSubscriber2UpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ViettelSubscriber2UpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      viettelSubscriber2Service = TestBed.inject(ViettelSubscriber2Service);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const viettelSubscriber2: IViettelSubscriber2 = { id: 456 };

        activatedRoute.data = of({ viettelSubscriber2 });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(viettelSubscriber2));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const viettelSubscriber2 = { id: 123 };
        spyOn(viettelSubscriber2Service, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ viettelSubscriber2 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: viettelSubscriber2 }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(viettelSubscriber2Service.update).toHaveBeenCalledWith(viettelSubscriber2);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const viettelSubscriber2 = new ViettelSubscriber2();
        spyOn(viettelSubscriber2Service, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ viettelSubscriber2 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: viettelSubscriber2 }));
        saveSubject.complete();

        // THEN
        expect(viettelSubscriber2Service.create).toHaveBeenCalledWith(viettelSubscriber2);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const viettelSubscriber2 = { id: 123 };
        spyOn(viettelSubscriber2Service, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ viettelSubscriber2 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(viettelSubscriber2Service.update).toHaveBeenCalledWith(viettelSubscriber2);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});

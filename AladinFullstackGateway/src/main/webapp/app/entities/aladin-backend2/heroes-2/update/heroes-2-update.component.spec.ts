jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { Heroes2Service } from '../service/heroes-2.service';
import { IHeroes2, Heroes2 } from '../heroes-2.model';
import { IViettelSubscriber2 } from 'app/entities/aladin-backend2/viettel-subscriber-2/viettel-subscriber-2.model';
import { ViettelSubscriber2Service } from 'app/entities/aladin-backend2/viettel-subscriber-2/service/viettel-subscriber-2.service';

import { Heroes2UpdateComponent } from './heroes-2-update.component';

describe('Component Tests', () => {
  describe('Heroes2 Management Update Component', () => {
    let comp: Heroes2UpdateComponent;
    let fixture: ComponentFixture<Heroes2UpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let heroes2Service: Heroes2Service;
    let viettelSubscriber2Service: ViettelSubscriber2Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [Heroes2UpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(Heroes2UpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Heroes2UpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      heroes2Service = TestBed.inject(Heroes2Service);
      viettelSubscriber2Service = TestBed.inject(ViettelSubscriber2Service);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call ViettelSubscriber2 query and add missing value', () => {
        const heroes2: IHeroes2 = { id: 456 };
        const viettelSubscriber2: IViettelSubscriber2 = { id: 73065 };
        heroes2.viettelSubscriber2 = viettelSubscriber2;

        const viettelSubscriber2Collection: IViettelSubscriber2[] = [{ id: 13542 }];
        spyOn(viettelSubscriber2Service, 'query').and.returnValue(of(new HttpResponse({ body: viettelSubscriber2Collection })));
        const additionalViettelSubscriber2s = [viettelSubscriber2];
        const expectedCollection: IViettelSubscriber2[] = [...additionalViettelSubscriber2s, ...viettelSubscriber2Collection];
        spyOn(viettelSubscriber2Service, 'addViettelSubscriber2ToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ heroes2 });
        comp.ngOnInit();

        expect(viettelSubscriber2Service.query).toHaveBeenCalled();
        expect(viettelSubscriber2Service.addViettelSubscriber2ToCollectionIfMissing).toHaveBeenCalledWith(
          viettelSubscriber2Collection,
          ...additionalViettelSubscriber2s
        );
        expect(comp.viettelSubscriber2sSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const heroes2: IHeroes2 = { id: 456 };
        const viettelSubscriber2: IViettelSubscriber2 = { id: 2692 };
        heroes2.viettelSubscriber2 = viettelSubscriber2;

        activatedRoute.data = of({ heroes2 });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(heroes2));
        expect(comp.viettelSubscriber2sSharedCollection).toContain(viettelSubscriber2);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const heroes2 = { id: 123 };
        spyOn(heroes2Service, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ heroes2 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: heroes2 }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(heroes2Service.update).toHaveBeenCalledWith(heroes2);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const heroes2 = new Heroes2();
        spyOn(heroes2Service, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ heroes2 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: heroes2 }));
        saveSubject.complete();

        // THEN
        expect(heroes2Service.create).toHaveBeenCalledWith(heroes2);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const heroes2 = { id: 123 };
        spyOn(heroes2Service, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ heroes2 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(heroes2Service.update).toHaveBeenCalledWith(heroes2);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackViettelSubscriber2ById', () => {
        it('Should return tracked ViettelSubscriber2 primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackViettelSubscriber2ById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});

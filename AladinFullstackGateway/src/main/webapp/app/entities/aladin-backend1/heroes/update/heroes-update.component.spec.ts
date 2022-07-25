jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { HeroesService } from '../service/heroes.service';
import { IHeroes, Heroes } from '../heroes.model';
import { IViettelSubscriber } from 'app/entities/aladin-backend1/viettel-subscriber/viettel-subscriber.model';
import { ViettelSubscriberService } from 'app/entities/aladin-backend1/viettel-subscriber/service/viettel-subscriber.service';

import { HeroesUpdateComponent } from './heroes-update.component';

describe('Component Tests', () => {
  describe('Heroes Management Update Component', () => {
    let comp: HeroesUpdateComponent;
    let fixture: ComponentFixture<HeroesUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let heroesService: HeroesService;
    let viettelSubscriberService: ViettelSubscriberService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [HeroesUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(HeroesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HeroesUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      heroesService = TestBed.inject(HeroesService);
      viettelSubscriberService = TestBed.inject(ViettelSubscriberService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call ViettelSubscriber query and add missing value', () => {
        const heroes: IHeroes = { id: 456 };
        const viettelSubscriber: IViettelSubscriber = { id: 95624 };
        heroes.viettelSubscriber = viettelSubscriber;

        const viettelSubscriberCollection: IViettelSubscriber[] = [{ id: 91647 }];
        spyOn(viettelSubscriberService, 'query').and.returnValue(of(new HttpResponse({ body: viettelSubscriberCollection })));
        const additionalViettelSubscribers = [viettelSubscriber];
        const expectedCollection: IViettelSubscriber[] = [...additionalViettelSubscribers, ...viettelSubscriberCollection];
        spyOn(viettelSubscriberService, 'addViettelSubscriberToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ heroes });
        comp.ngOnInit();

        expect(viettelSubscriberService.query).toHaveBeenCalled();
        expect(viettelSubscriberService.addViettelSubscriberToCollectionIfMissing).toHaveBeenCalledWith(
          viettelSubscriberCollection,
          ...additionalViettelSubscribers
        );
        expect(comp.viettelSubscribersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const heroes: IHeroes = { id: 456 };
        const viettelSubscriber: IViettelSubscriber = { id: 61446 };
        heroes.viettelSubscriber = viettelSubscriber;

        activatedRoute.data = of({ heroes });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(heroes));
        expect(comp.viettelSubscribersSharedCollection).toContain(viettelSubscriber);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const heroes = { id: 123 };
        spyOn(heroesService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ heroes });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: heroes }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(heroesService.update).toHaveBeenCalledWith(heroes);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const heroes = new Heroes();
        spyOn(heroesService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ heroes });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: heroes }));
        saveSubject.complete();

        // THEN
        expect(heroesService.create).toHaveBeenCalledWith(heroes);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const heroes = { id: 123 };
        spyOn(heroesService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ heroes });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(heroesService.update).toHaveBeenCalledWith(heroes);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackViettelSubscriberById', () => {
        it('Should return tracked ViettelSubscriber primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackViettelSubscriberById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});

jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RecruitmenService } from '../service/recruitmen.service';
import { IRecruitmen, Recruitmen } from '../recruitmen.model';

import { RecruitmenUpdateComponent } from './recruitmen-update.component';

describe('Component Tests', () => {
  describe('Recruitmen Management Update Component', () => {
    let comp: RecruitmenUpdateComponent;
    let fixture: ComponentFixture<RecruitmenUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let recruitmenService: RecruitmenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RecruitmenUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RecruitmenUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecruitmenUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      recruitmenService = TestBed.inject(RecruitmenService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const recruitmen: IRecruitmen = { id: 456 };

        activatedRoute.data = of({ recruitmen });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(recruitmen));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Recruitmen>>();
        const recruitmen = { id: 123 };
        jest.spyOn(recruitmenService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ recruitmen });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: recruitmen }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(recruitmenService.update).toHaveBeenCalledWith(recruitmen);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Recruitmen>>();
        const recruitmen = new Recruitmen();
        jest.spyOn(recruitmenService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ recruitmen });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: recruitmen }));
        saveSubject.complete();

        // THEN
        expect(recruitmenService.create).toHaveBeenCalledWith(recruitmen);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Recruitmen>>();
        const recruitmen = { id: 123 };
        jest.spyOn(recruitmenService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ recruitmen });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(recruitmenService.update).toHaveBeenCalledWith(recruitmen);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});

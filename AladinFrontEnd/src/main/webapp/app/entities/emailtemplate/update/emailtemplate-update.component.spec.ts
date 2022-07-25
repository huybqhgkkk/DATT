jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EmailtemplateService } from '../service/emailtemplate.service';
import { IEmailtemplate, Emailtemplate } from '../emailtemplate.model';

import { EmailtemplateUpdateComponent } from './emailtemplate-update.component';

describe('Emailtemplate Management Update Component', () => {
  let comp: EmailtemplateUpdateComponent;
  let fixture: ComponentFixture<EmailtemplateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let emailtemplateService: EmailtemplateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [EmailtemplateUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(EmailtemplateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmailtemplateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    emailtemplateService = TestBed.inject(EmailtemplateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const emailtemplate: IEmailtemplate = { id: 456 };

      activatedRoute.data = of({ emailtemplate });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(emailtemplate));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Emailtemplate>>();
      const emailtemplate = { id: 123 };
      jest.spyOn(emailtemplateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emailtemplate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: emailtemplate }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(emailtemplateService.update).toHaveBeenCalledWith(emailtemplate);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Emailtemplate>>();
      const emailtemplate = new Emailtemplate();
      jest.spyOn(emailtemplateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emailtemplate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: emailtemplate }));
      saveSubject.complete();

      // THEN
      expect(emailtemplateService.create).toHaveBeenCalledWith(emailtemplate);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Emailtemplate>>();
      const emailtemplate = { id: 123 };
      jest.spyOn(emailtemplateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emailtemplate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(emailtemplateService.update).toHaveBeenCalledWith(emailtemplate);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

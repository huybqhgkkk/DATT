import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IEmailtemplate, Emailtemplate } from '../emailtemplate.model';
import { EmailtemplateService } from '../service/emailtemplate.service';

@Component({
  selector: 'jhi-emailtemplate-update',
  templateUrl: './emailtemplate-update.component.html',
})
export class EmailtemplateUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    templatename: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
    subject: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(1000)]],
    hyperlink: [],
    datetime: [],
    content: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(2000)]],
  });

  constructor(protected emailtemplateService: EmailtemplateService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emailtemplate }) => {
      if (emailtemplate.id === undefined) {
        const today = dayjs().startOf('day');
        emailtemplate.datetime = today;
      }

      this.updateForm(emailtemplate);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emailtemplate = this.createFromForm();
    if (emailtemplate.id !== undefined) {
      this.subscribeToSaveResponse(this.emailtemplateService.update(emailtemplate));
    } else {
      this.subscribeToSaveResponse(this.emailtemplateService.create(emailtemplate));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmailtemplate>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(emailtemplate: IEmailtemplate): void {
    this.editForm.patchValue({
      id: emailtemplate.id,
      templatename: emailtemplate.templatename,
      subject: emailtemplate.subject,
      hyperlink: emailtemplate.hyperlink,
      datetime: emailtemplate.datetime ? emailtemplate.datetime.format(DATE_TIME_FORMAT) : null,
      content: emailtemplate.content,
    });
  }

  protected createFromForm(): IEmailtemplate {
    return {
      ...new Emailtemplate(),
      id: this.editForm.get(['id'])!.value,
      templatename: this.editForm.get(['templatename'])!.value,
      subject: this.editForm.get(['subject'])!.value,
      hyperlink: this.editForm.get(['hyperlink'])!.value,
      datetime: this.editForm.get(['datetime'])!.value ? dayjs(this.editForm.get(['datetime'])!.value, DATE_TIME_FORMAT) : undefined,
      content: this.editForm.get(['content'])!.value,
    };
  }
}

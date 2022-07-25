import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IContact, Contact } from '../contact.model';
import { ContactService } from '../service/contact.service';

@Component({
  selector: 'jhi-contact-update',
  templateUrl: './contact-update.component.html',
})
export class ContactUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    email: [null, [Validators.required]],
    message: [],
    phone: [null, [Validators.required]],
    datetime: [],
    jobtitle: [],
    company: [],
    interest: [],
  });

  constructor(protected contactService: ContactService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contact }) => {
      if (contact.id === undefined) {
        const today = dayjs().startOf('day');
        contact.datetime = today;
      }

      this.updateForm(contact);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contact = this.createFromForm();
    if (contact.id !== undefined) {
      this.subscribeToSaveResponse(this.contactService.update(contact));
    } else {
      this.subscribeToSaveResponse(this.contactService.create(contact));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContact>>): void {
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

  protected updateForm(contact: IContact): void {
    this.editForm.patchValue({
      id: contact.id,
      name: contact.name,
      email: contact.email,
      message: contact.message,
      phone: contact.phone,
      datetime: contact.datecontact ? contact.datecontact.format(DATE_TIME_FORMAT) : null,
      jobtitle: contact.jobtitle,
      company: contact.company,
      interest: contact.interest,
    });
  }

  protected createFromForm(): IContact {
    return {
      ...new Contact(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      email: this.editForm.get(['email'])!.value,
      message: this.editForm.get(['message'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      datecontact: this.editForm.get(['datetime'])!.value ? dayjs(this.editForm.get(['datetime'])!.value, DATE_TIME_FORMAT) : undefined,
      jobtitle: this.editForm.get(['jobtitle'])!.value,
      company: this.editForm.get(['company'])!.value,
      interest: this.editForm.get(['interest'])!.value,
    };
  }
}

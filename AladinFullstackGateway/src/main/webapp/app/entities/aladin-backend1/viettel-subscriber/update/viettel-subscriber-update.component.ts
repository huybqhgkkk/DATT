import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IViettelSubscriber, ViettelSubscriber } from '../viettel-subscriber.model';
import { ViettelSubscriberService } from '../service/viettel-subscriber.service';

@Component({
  selector: 'jhi-viettel-subscriber-update',
  templateUrl: './viettel-subscriber-update.component.html',
})
export class ViettelSubscriberUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    msisdn: [null, [Validators.minLength(9), Validators.maxLength(11)]],
  });

  constructor(
    protected viettelSubscriberService: ViettelSubscriberService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ viettelSubscriber }) => {
      this.updateForm(viettelSubscriber);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const viettelSubscriber = this.createFromForm();
    if (viettelSubscriber.id !== undefined) {
      this.subscribeToSaveResponse(this.viettelSubscriberService.update(viettelSubscriber));
    } else {
      this.subscribeToSaveResponse(this.viettelSubscriberService.create(viettelSubscriber));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IViettelSubscriber>>): void {
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

  protected updateForm(viettelSubscriber: IViettelSubscriber): void {
    this.editForm.patchValue({
      id: viettelSubscriber.id,
      name: viettelSubscriber.name,
      msisdn: viettelSubscriber.msisdn,
    });
  }

  protected createFromForm(): IViettelSubscriber {
    return {
      ...new ViettelSubscriber(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      msisdn: this.editForm.get(['msisdn'])!.value,
    };
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IViettelSubscriber2, ViettelSubscriber2 } from '../viettel-subscriber-2.model';
import { ViettelSubscriber2Service } from '../service/viettel-subscriber-2.service';

@Component({
  selector: 'jhi-viettel-subscriber-2-update',
  templateUrl: './viettel-subscriber-2-update.component.html',
})
export class ViettelSubscriber2UpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    msisdn: [null, [Validators.minLength(9), Validators.maxLength(11)]],
  });

  constructor(
    protected viettelSubscriber2Service: ViettelSubscriber2Service,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ viettelSubscriber2 }) => {
      this.updateForm(viettelSubscriber2);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const viettelSubscriber2 = this.createFromForm();
    if (viettelSubscriber2.id !== undefined) {
      this.subscribeToSaveResponse(this.viettelSubscriber2Service.update(viettelSubscriber2));
    } else {
      this.subscribeToSaveResponse(this.viettelSubscriber2Service.create(viettelSubscriber2));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IViettelSubscriber2>>): void {
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

  protected updateForm(viettelSubscriber2: IViettelSubscriber2): void {
    this.editForm.patchValue({
      id: viettelSubscriber2.id,
      name: viettelSubscriber2.name,
      msisdn: viettelSubscriber2.msisdn,
    });
  }

  protected createFromForm(): IViettelSubscriber2 {
    return {
      ...new ViettelSubscriber2(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      msisdn: this.editForm.get(['msisdn'])!.value,
    };
  }
}

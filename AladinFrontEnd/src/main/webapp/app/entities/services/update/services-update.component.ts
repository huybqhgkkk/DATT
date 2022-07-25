import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IServices, Services } from '../services.model';
import { ServicesService } from '../service/services.service';

@Component({
  selector: 'jhi-services-update',
  templateUrl: './services-update.component.html',
})
export class ServicesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    type: [],
    description: [],
    reason: [],
  });

  constructor(protected servicesService: ServicesService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ services }) => {
      this.updateForm(services);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const services = this.createFromForm();
    if (services.id !== undefined) {
      this.subscribeToSaveResponse(this.servicesService.update(services));
    } else {
      this.subscribeToSaveResponse(this.servicesService.create(services));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServices>>): void {
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

  protected updateForm(services: IServices): void {
    this.editForm.patchValue({
      id: services.id,
      type: services.type,
      description: services.description,
      reason: services.reason,
    });
  }

  protected createFromForm(): IServices {
    return {
      ...new Services(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      description: this.editForm.get(['description'])!.value,
      reason: this.editForm.get(['reason'])!.value,
    };
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICandidate, Candidate } from '../candidate.model';
import { CandidateService } from '../service/candidate.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-candidate-update',
  templateUrl: './candidate-update.component.html',
})
export class CandidateUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    phone: [null, [Validators.required]],
    email: [null, [Validators.required]],
    position: [null, [Validators.required]],
    birthday: [null, [Validators.required]],
    location: [null, [Validators.required]],
    preference: [],
    education: [null, [Validators.required]],
    experience: [null, [Validators.required]],
    target: [],
    relationship: [],
    fullname: [],
    sex: [],
    cv: [],
    cvContentType: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected candidateService: CandidateService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ candidate }) => {
      if (candidate.id === undefined) {
        const today = dayjs().startOf('day');
        candidate.birthday = today;
        candidate.timeregister = today;
      }

      this.updateForm(candidate);
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('AladinTechApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const candidate = this.createFromForm();
    if (candidate.id !== undefined) {
      this.subscribeToSaveResponse(this.candidateService.update(candidate));
    } else {
      this.subscribeToSaveResponse(this.candidateService.create(candidate));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidate>>): void {
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

  protected updateForm(candidate: ICandidate): void {
    this.editForm.patchValue({
      id: candidate.id,
      phone: candidate.phone,
      email: candidate.email,
      position: candidate.position,
      birthday: candidate.birthday ? candidate.birthday.format(DATE_TIME_FORMAT) : null,
      location: candidate.location,
      preference: candidate.preference,
      education: candidate.education,
      experience: candidate.experience,
      target: candidate.target,
      relationship: candidate.relationship,
      fullname: candidate.fullname,
      sex: candidate.sex,
      cv: candidate.cv,
      cvContentType: candidate.cvContentType,
    });
  }

  protected createFromForm(): ICandidate {
    return {
      ...new Candidate(),
      id: this.editForm.get(['id'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      email: this.editForm.get(['email'])!.value,
      position: this.editForm.get(['position'])!.value,
      birthday: this.editForm.get(['birthday'])!.value ? dayjs(this.editForm.get(['birthday'])!.value, DATE_TIME_FORMAT) : undefined,
      location: this.editForm.get(['location'])!.value,
      preference: this.editForm.get(['preference'])!.value,
      education: this.editForm.get(['education'])!.value,
      experience: this.editForm.get(['experience'])!.value,
      target: this.editForm.get(['target'])!.value,
      relationship: this.editForm.get(['relationship'])!.value,
      fullname: this.editForm.get(['fullname'])!.value,
      sex: this.editForm.get(['sex'])!.value,
      cvContentType: this.editForm.get(['cvContentType'])!.value,
      cv: this.editForm.get(['cv'])!.value,
    };
  }
}

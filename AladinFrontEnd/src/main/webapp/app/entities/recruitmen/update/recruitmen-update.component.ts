import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IRecruitmen, Recruitmen } from '../recruitmen.model';
import { RecruitmenService } from '../service/recruitmen.service';
import * as dayjs from 'dayjs';

@Component({
  selector: 'jhi-recruitmen-update',
  templateUrl: './recruitmen-update.component.html',
})
export class RecruitmenUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    position: [null, [Validators.required]],
    description: [null, [Validators.required]],
    require: [null, [Validators.required]],
    benefit: [null, [Validators.required]],
    amount: [null, [Validators.required]],
    job: [],
    location: [],
    duration: [],
    level: [],
  });

  ok: any;
  constructor(protected recruitmenService: RecruitmenService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recruitmen }) => {
      const today = dayjs().startOf('day').format('YYYY-MM-DD HH:mm');
      recruitmen.datetime = today;
      this.ok = today;
      this.updateForm(recruitmen);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const recruitmen = this.createFromForm();
    if (recruitmen.id !== undefined) {
      this.subscribeToSaveResponse(this.recruitmenService.update(recruitmen));
    } else {
      this.subscribeToSaveResponse(this.recruitmenService.create(recruitmen));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecruitmen>>): void {
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

  protected updateForm(recruitmen: IRecruitmen): void {
    this.editForm.patchValue({
      id: recruitmen.id,
      position: recruitmen.position,
      description: recruitmen.description,
      require: recruitmen.require,
      benefit: recruitmen.benefit,
      amount: recruitmen.amount,
      job: recruitmen.job,
      location: recruitmen.location,
      duration: recruitmen.duration,
      level: recruitmen.level,
    });
  }

  protected createFromForm(): IRecruitmen {
    return {
      ...new Recruitmen(),
      id: this.editForm.get(['id'])!.value,
      position: this.editForm.get(['position'])!.value,
      description: this.editForm.get(['description'])!.value,
      require: this.editForm.get(['require'])!.value,
      benefit: this.editForm.get(['benefit'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      job: this.editForm.get(['job'])!.value,
      location: this.editForm.get(['location'])!.value,
      duration: this.editForm.get(['duration'])!.value,
      level: this.editForm.get(['level'])!.value,
    };
  }
}

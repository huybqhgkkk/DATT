import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IHeroes3, Heroes3 } from '../heroes-3.model';
import { Heroes3Service } from '../service/heroes-3.service';
import { IViettelSubscriber2 } from 'app/entities/aladin-backend2/viettel-subscriber-2/viettel-subscriber-2.model';
import { ViettelSubscriber2Service } from 'app/entities/aladin-backend2/viettel-subscriber-2/service/viettel-subscriber-2.service';

@Component({
  selector: 'jhi-heroes-3-update',
  templateUrl: './heroes-3-update.component.html',
})
export class Heroes3UpdateComponent implements OnInit {
  isSaving = false;

  viettelSubscriber2sSharedCollection: IViettelSubscriber2[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.minLength(10), Validators.maxLength(30)]],
    create_time: [],
    viettelSubscriber2: [],
  });

  constructor(
    protected heroes3Service: Heroes3Service,
    protected viettelSubscriber2Service: ViettelSubscriber2Service,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ heroes3 }) => {
      if (heroes3.id === undefined) {
        const today = dayjs().startOf('day');
        heroes3.create_time = today;
      }

      this.updateForm(heroes3);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const heroes3 = this.createFromForm();
    if (heroes3.id !== undefined) {
      this.subscribeToSaveResponse(this.heroes3Service.update(heroes3));
    } else {
      this.subscribeToSaveResponse(this.heroes3Service.create(heroes3));
    }
  }

  trackViettelSubscriber2ById(index: number, item: IViettelSubscriber2): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHeroes3>>): void {
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

  protected updateForm(heroes3: IHeroes3): void {
    this.editForm.patchValue({
      id: heroes3.id,
      name: heroes3.name,
      create_time: heroes3.create_time ? heroes3.create_time.format(DATE_TIME_FORMAT) : null,
      viettelSubscriber2: heroes3.viettelSubscriber2,
    });

    this.viettelSubscriber2sSharedCollection = this.viettelSubscriber2Service.addViettelSubscriber2ToCollectionIfMissing(
      this.viettelSubscriber2sSharedCollection,
      heroes3.viettelSubscriber2
    );
  }

  protected loadRelationshipsOptions(): void {
    this.viettelSubscriber2Service
      .query()
      .pipe(map((res: HttpResponse<IViettelSubscriber2[]>) => res.body ?? []))
      .pipe(
        map((viettelSubscriber2s: IViettelSubscriber2[]) =>
          this.viettelSubscriber2Service.addViettelSubscriber2ToCollectionIfMissing(
            viettelSubscriber2s,
            this.editForm.get('viettelSubscriber2')!.value
          )
        )
      )
      .subscribe((viettelSubscriber2s: IViettelSubscriber2[]) => (this.viettelSubscriber2sSharedCollection = viettelSubscriber2s));
  }

  protected createFromForm(): IHeroes3 {
    return {
      ...new Heroes3(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      create_time: this.editForm.get(['create_time'])!.value
        ? dayjs(this.editForm.get(['create_time'])!.value, DATE_TIME_FORMAT)
        : undefined,
      viettelSubscriber2: this.editForm.get(['viettelSubscriber2'])!.value,
    };
  }
}

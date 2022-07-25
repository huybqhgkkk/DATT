import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IHeroes, Heroes } from '../heroes.model';
import { HeroesService } from '../service/heroes.service';
import { IViettelSubscriber } from 'app/entities/aladin-backend1/viettel-subscriber/viettel-subscriber.model';
import { ViettelSubscriberService } from 'app/entities/aladin-backend1/viettel-subscriber/service/viettel-subscriber.service';

@Component({
  selector: 'jhi-heroes-update',
  templateUrl: './heroes-update.component.html',
})
export class HeroesUpdateComponent implements OnInit {
  isSaving = false;

  viettelSubscribersSharedCollection: IViettelSubscriber[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.minLength(10), Validators.maxLength(30)]],
    create_time: [],
    viettelSubscriber: [],
  });

  constructor(
    protected heroesService: HeroesService,
    protected viettelSubscriberService: ViettelSubscriberService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ heroes }) => {
      if (heroes.id === undefined) {
        const today = dayjs().startOf('day');
        heroes.create_time = today;
      }

      this.updateForm(heroes);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const heroes = this.createFromForm();
    if (heroes.id !== undefined) {
      this.subscribeToSaveResponse(this.heroesService.update(heroes));
    } else {
      this.subscribeToSaveResponse(this.heroesService.create(heroes));
    }
  }

  trackViettelSubscriberById(index: number, item: IViettelSubscriber): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHeroes>>): void {
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

  protected updateForm(heroes: IHeroes): void {
    this.editForm.patchValue({
      id: heroes.id,
      name: heroes.name,
      create_time: heroes.create_time ? heroes.create_time.format(DATE_TIME_FORMAT) : null,
      viettelSubscriber: heroes.viettelSubscriber,
    });

    this.viettelSubscribersSharedCollection = this.viettelSubscriberService.addViettelSubscriberToCollectionIfMissing(
      this.viettelSubscribersSharedCollection,
      heroes.viettelSubscriber
    );
  }

  protected loadRelationshipsOptions(): void {
    this.viettelSubscriberService
      .query()
      .pipe(map((res: HttpResponse<IViettelSubscriber[]>) => res.body ?? []))
      .pipe(
        map((viettelSubscribers: IViettelSubscriber[]) =>
          this.viettelSubscriberService.addViettelSubscriberToCollectionIfMissing(
            viettelSubscribers,
            this.editForm.get('viettelSubscriber')!.value
          )
        )
      )
      .subscribe((viettelSubscribers: IViettelSubscriber[]) => (this.viettelSubscribersSharedCollection = viettelSubscribers));
  }

  protected createFromForm(): IHeroes {
    return {
      ...new Heroes(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      create_time: this.editForm.get(['create_time'])!.value
        ? dayjs(this.editForm.get(['create_time'])!.value, DATE_TIME_FORMAT)
        : undefined,
      viettelSubscriber: this.editForm.get(['viettelSubscriber'])!.value,
    };
  }
}

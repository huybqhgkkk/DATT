import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IHeroes2, Heroes2 } from '../heroes-2.model';
import { Heroes2Service } from '../service/heroes-2.service';
import { IViettelSubscriber2 } from 'app/entities/aladin-backend2/viettel-subscriber-2/viettel-subscriber-2.model';
import { ViettelSubscriber2Service } from 'app/entities/aladin-backend2/viettel-subscriber-2/service/viettel-subscriber-2.service';

@Component({
  selector: 'jhi-heroes-2-update',
  templateUrl: './heroes-2-update.component.html',
})
export class Heroes2UpdateComponent implements OnInit {
  isSaving = false;

  viettelSubscriber2sSharedCollection: IViettelSubscriber2[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.minLength(10), Validators.maxLength(30)]],
    create_time: [],
    viettelSubscriber2: [],
  });

  constructor(
    protected heroes2Service: Heroes2Service,
    protected viettelSubscriber2Service: ViettelSubscriber2Service,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ heroes2 }) => {
      if (heroes2.id === undefined) {
        const today = dayjs().startOf('day');
        heroes2.create_time = today;
      }

      this.updateForm(heroes2);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const heroes2 = this.createFromForm();
    if (heroes2.id !== undefined) {
      this.subscribeToSaveResponse(this.heroes2Service.update(heroes2));
    } else {
      this.subscribeToSaveResponse(this.heroes2Service.create(heroes2));
    }
  }

  trackViettelSubscriber2ById(index: number, item: IViettelSubscriber2): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHeroes2>>): void {
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

  protected updateForm(heroes2: IHeroes2): void {
    this.editForm.patchValue({
      id: heroes2.id,
      name: heroes2.name,
      create_time: heroes2.create_time ? heroes2.create_time.format(DATE_TIME_FORMAT) : null,
      viettelSubscriber2: heroes2.viettelSubscriber2,
    });

    this.viettelSubscriber2sSharedCollection = this.viettelSubscriber2Service.addViettelSubscriber2ToCollectionIfMissing(
      this.viettelSubscriber2sSharedCollection,
      heroes2.viettelSubscriber2
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

  protected createFromForm(): IHeroes2 {
    return {
      ...new Heroes2(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      create_time: this.editForm.get(['create_time'])!.value
        ? dayjs(this.editForm.get(['create_time'])!.value, DATE_TIME_FORMAT)
        : undefined,
      viettelSubscriber2: this.editForm.get(['viettelSubscriber2'])!.value,
    };
  }
}

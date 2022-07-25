import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IViettelSubscriber } from '../viettel-subscriber.model';

@Component({
  selector: 'jhi-viettel-subscriber-detail',
  templateUrl: './viettel-subscriber-detail.component.html',
})
export class ViettelSubscriberDetailComponent implements OnInit {
  viettelSubscriber: IViettelSubscriber | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ viettelSubscriber }) => {
      this.viettelSubscriber = viettelSubscriber;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

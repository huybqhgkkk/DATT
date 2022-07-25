import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IViettelSubscriber2 } from '../viettel-subscriber-2.model';

@Component({
  selector: 'jhi-viettel-subscriber-2-detail',
  templateUrl: './viettel-subscriber-2-detail.component.html',
})
export class ViettelSubscriber2DetailComponent implements OnInit {
  viettelSubscriber2: IViettelSubscriber2 | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ viettelSubscriber2 }) => {
      this.viettelSubscriber2 = viettelSubscriber2;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

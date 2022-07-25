import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecruitmen } from '../recruitmen.model';

@Component({
  selector: 'jhi-recruitmen-detail',
  templateUrl: './recruitmen-detail.component.html',
  styleUrls: ['./recruitment.component.scss'],
})
export class RecruitmenDetailComponent implements OnInit {
  recruitmen: IRecruitmen | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recruitmen }) => {
      this.recruitmen = recruitmen;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

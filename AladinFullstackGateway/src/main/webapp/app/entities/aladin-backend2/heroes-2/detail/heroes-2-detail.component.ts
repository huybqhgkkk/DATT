import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHeroes2 } from '../heroes-2.model';

@Component({
  selector: 'jhi-heroes-2-detail',
  templateUrl: './heroes-2-detail.component.html',
})
export class Heroes2DetailComponent implements OnInit {
  heroes2: IHeroes2 | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ heroes2 }) => {
      this.heroes2 = heroes2;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

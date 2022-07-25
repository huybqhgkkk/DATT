import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHeroes3 } from '../heroes-3.model';

@Component({
  selector: 'jhi-heroes-3-detail',
  templateUrl: './heroes-3-detail.component.html',
})
export class Heroes3DetailComponent implements OnInit {
  heroes3: IHeroes3 | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ heroes3 }) => {
      this.heroes3 = heroes3;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

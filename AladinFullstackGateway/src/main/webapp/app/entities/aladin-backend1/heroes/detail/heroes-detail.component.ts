import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHeroes } from '../heroes.model';

@Component({
  selector: 'jhi-heroes-detail',
  templateUrl: './heroes-detail.component.html',
})
export class HeroesDetailComponent implements OnInit {
  heroes: IHeroes | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ heroes }) => {
      this.heroes = heroes;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

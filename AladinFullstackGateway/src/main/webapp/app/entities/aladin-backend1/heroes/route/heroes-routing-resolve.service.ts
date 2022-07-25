import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHeroes, Heroes } from '../heroes.model';
import { HeroesService } from '../service/heroes.service';

@Injectable({ providedIn: 'root' })
export class HeroesRoutingResolveService implements Resolve<IHeroes> {
  constructor(protected service: HeroesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHeroes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((heroes: HttpResponse<Heroes>) => {
          if (heroes.body) {
            return of(heroes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Heroes());
  }
}

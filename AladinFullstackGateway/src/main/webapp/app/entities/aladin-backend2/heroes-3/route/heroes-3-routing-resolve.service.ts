import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHeroes3, Heroes3 } from '../heroes-3.model';
import { Heroes3Service } from '../service/heroes-3.service';

@Injectable({ providedIn: 'root' })
export class Heroes3RoutingResolveService implements Resolve<IHeroes3> {
  constructor(protected service: Heroes3Service, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHeroes3> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((heroes3: HttpResponse<Heroes3>) => {
          if (heroes3.body) {
            return of(heroes3.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Heroes3());
  }
}

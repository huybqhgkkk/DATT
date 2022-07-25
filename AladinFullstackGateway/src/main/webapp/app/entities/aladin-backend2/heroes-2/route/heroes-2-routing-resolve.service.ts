import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHeroes2, Heroes2 } from '../heroes-2.model';
import { Heroes2Service } from '../service/heroes-2.service';

@Injectable({ providedIn: 'root' })
export class Heroes2RoutingResolveService implements Resolve<IHeroes2> {
  constructor(protected service: Heroes2Service, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHeroes2> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((heroes2: HttpResponse<Heroes2>) => {
          if (heroes2.body) {
            return of(heroes2.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Heroes2());
  }
}

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRecruitmen, Recruitmen } from '../recruitmen.model';
import { RecruitmenService } from '../service/recruitmen.service';

@Injectable({ providedIn: 'root' })
export class RecruitmenRoutingResolveService implements Resolve<IRecruitmen> {
  constructor(protected service: RecruitmenService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRecruitmen> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((recruitmen: HttpResponse<Recruitmen>) => {
          if (recruitmen.body) {
            return of(recruitmen.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Recruitmen());
  }
}

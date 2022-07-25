import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IViettelSubscriber2, ViettelSubscriber2 } from '../viettel-subscriber-2.model';
import { ViettelSubscriber2Service } from '../service/viettel-subscriber-2.service';

@Injectable({ providedIn: 'root' })
export class ViettelSubscriber2RoutingResolveService implements Resolve<IViettelSubscriber2> {
  constructor(protected service: ViettelSubscriber2Service, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IViettelSubscriber2> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((viettelSubscriber2: HttpResponse<ViettelSubscriber2>) => {
          if (viettelSubscriber2.body) {
            return of(viettelSubscriber2.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ViettelSubscriber2());
  }
}

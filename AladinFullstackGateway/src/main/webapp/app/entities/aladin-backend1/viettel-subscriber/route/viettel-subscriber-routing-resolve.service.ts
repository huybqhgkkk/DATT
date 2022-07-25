import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IViettelSubscriber, ViettelSubscriber } from '../viettel-subscriber.model';
import { ViettelSubscriberService } from '../service/viettel-subscriber.service';

@Injectable({ providedIn: 'root' })
export class ViettelSubscriberRoutingResolveService implements Resolve<IViettelSubscriber> {
  constructor(protected service: ViettelSubscriberService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IViettelSubscriber> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((viettelSubscriber: HttpResponse<ViettelSubscriber>) => {
          if (viettelSubscriber.body) {
            return of(viettelSubscriber.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ViettelSubscriber());
  }
}

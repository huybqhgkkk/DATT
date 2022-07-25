import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmailtemplate, Emailtemplate } from '../emailtemplate.model';
import { EmailtemplateService } from '../service/emailtemplate.service';

@Injectable({ providedIn: 'root' })
export class EmailtemplateRoutingResolveService implements Resolve<IEmailtemplate> {
  constructor(protected service: EmailtemplateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmailtemplate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((emailtemplate: HttpResponse<Emailtemplate>) => {
          if (emailtemplate.body) {
            return of(emailtemplate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Emailtemplate());
  }
}

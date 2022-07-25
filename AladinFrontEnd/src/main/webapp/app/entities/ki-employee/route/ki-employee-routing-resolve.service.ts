import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKiEmployee, KiEmployee } from '../ki-employee.model';
import { KiEmployeeService } from '../service/ki-employee.service';

@Injectable({ providedIn: 'root' })
export class KiEmployeeRoutingResolveService implements Resolve<IKiEmployee> {
  constructor(protected service: KiEmployeeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKiEmployee> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((kiEmployee: HttpResponse<KiEmployee>) => {
          if (kiEmployee.body) {
            return of(kiEmployee.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new KiEmployee());
  }
}

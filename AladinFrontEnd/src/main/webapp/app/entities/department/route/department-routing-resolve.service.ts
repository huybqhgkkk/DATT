import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router } from '@angular/router';
import { Observable, of } from 'rxjs';

import { Department, IDepartment } from '../department.model';
import { DepartmentService } from '../service/department.service';

@Injectable({ providedIn: 'root' })
export class DepartmentRoutingResolveService implements Resolve<IDepartment> {
  constructor(protected service: DepartmentService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDepartment> | Observable<never> {
    const departmentsname = route.params['departmentsname'];
    if (departmentsname) {
      this.service.findPB(departmentsname).subscribe(res => {
        console.log(111, res.body);
      });
    }
    return of(new Department());
  }
}

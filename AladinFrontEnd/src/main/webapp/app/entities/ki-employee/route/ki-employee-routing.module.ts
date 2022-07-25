import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { KiEmployeeComponent } from '../list/ki-employee.component';
import { KiEmployeeDetailComponent } from '../detail/ki-employee-detail.component';
import { KiEmployeeUpdateComponent } from '../update/ki-employee-update.component';
import { KiEmployeeRoutingResolveService } from './ki-employee-routing-resolve.service';
import {kiEmployeeCreateComponent} from "../create/ki-employee-create.component";

const kiEmployeeRoute: Routes = [
  {
    path: '',
    component: KiEmployeeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KiEmployeeDetailComponent,
    resolve: {
      kiEmployee: KiEmployeeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: kiEmployeeCreateComponent,
    resolve: {
      kiEmployee: KiEmployeeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KiEmployeeUpdateComponent,
    resolve: {
      kiEmployee: KiEmployeeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(kiEmployeeRoute)],
  exports: [RouterModule],
})
export class KiEmployeeRoutingModule {}

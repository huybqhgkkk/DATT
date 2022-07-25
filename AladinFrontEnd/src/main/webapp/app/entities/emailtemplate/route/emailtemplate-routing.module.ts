import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EmailtemplateComponent } from '../list/emailtemplate.component';
import { EmailtemplateDetailComponent } from '../detail/emailtemplate-detail.component';
import { EmailtemplateUpdateComponent } from '../update/emailtemplate-update.component';
import { EmailtemplateRoutingResolveService } from './emailtemplate-routing-resolve.service';

const emailtemplateRoute: Routes = [
  {
    path: '',
    component: EmailtemplateComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmailtemplateDetailComponent,
    resolve: {
      emailtemplate: EmailtemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmailtemplateUpdateComponent,
    resolve: {
      emailtemplate: EmailtemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmailtemplateUpdateComponent,
    resolve: {
      emailtemplate: EmailtemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(emailtemplateRoute)],
  exports: [RouterModule],
})
export class EmailtemplateRoutingModule {}

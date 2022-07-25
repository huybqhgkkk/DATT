import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RecruitmenComponent } from '../list/recruitmen.component';
import { RecruitmenDetailComponent } from '../detail/recruitmen-detail.component';
import { RecruitmenUpdateComponent } from '../update/recruitmen-update.component';
import { RecruitmenRoutingResolveService } from './recruitmen-routing-resolve.service';

const recruitmenRoute: Routes = [
  {
    path: '',
    component: RecruitmenComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RecruitmenDetailComponent,
    resolve: {
      recruitmen: RecruitmenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RecruitmenUpdateComponent,
    resolve: {
      recruitmen: RecruitmenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RecruitmenUpdateComponent,
    resolve: {
      recruitmen: RecruitmenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(recruitmenRoute)],
  exports: [RouterModule],
})
export class RecruitmenRoutingModule {}

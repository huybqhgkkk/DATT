import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { Heroes3Component } from '../list/heroes-3.component';
import { Heroes3DetailComponent } from '../detail/heroes-3-detail.component';
import { Heroes3UpdateComponent } from '../update/heroes-3-update.component';
import { Heroes3RoutingResolveService } from './heroes-3-routing-resolve.service';

const heroes3Route: Routes = [
  {
    path: '',
    component: Heroes3Component,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: Heroes3DetailComponent,
    resolve: {
      heroes3: Heroes3RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: Heroes3UpdateComponent,
    resolve: {
      heroes3: Heroes3RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: Heroes3UpdateComponent,
    resolve: {
      heroes3: Heroes3RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(heroes3Route)],
  exports: [RouterModule],
})
export class Heroes3RoutingModule {}

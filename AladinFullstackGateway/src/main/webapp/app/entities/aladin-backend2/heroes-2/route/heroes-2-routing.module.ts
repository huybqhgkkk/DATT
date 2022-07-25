import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { Heroes2Component } from '../list/heroes-2.component';
import { Heroes2DetailComponent } from '../detail/heroes-2-detail.component';
import { Heroes2UpdateComponent } from '../update/heroes-2-update.component';
import { Heroes2RoutingResolveService } from './heroes-2-routing-resolve.service';

const heroes2Route: Routes = [
  {
    path: '',
    component: Heroes2Component,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: Heroes2DetailComponent,
    resolve: {
      heroes2: Heroes2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: Heroes2UpdateComponent,
    resolve: {
      heroes2: Heroes2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: Heroes2UpdateComponent,
    resolve: {
      heroes2: Heroes2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(heroes2Route)],
  exports: [RouterModule],
})
export class Heroes2RoutingModule {}

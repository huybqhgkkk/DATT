import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ViettelSubscriber2Component } from '../list/viettel-subscriber-2.component';
import { ViettelSubscriber2DetailComponent } from '../detail/viettel-subscriber-2-detail.component';
import { ViettelSubscriber2UpdateComponent } from '../update/viettel-subscriber-2-update.component';
import { ViettelSubscriber2RoutingResolveService } from './viettel-subscriber-2-routing-resolve.service';

const viettelSubscriber2Route: Routes = [
  {
    path: '',
    component: ViettelSubscriber2Component,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ViettelSubscriber2DetailComponent,
    resolve: {
      viettelSubscriber2: ViettelSubscriber2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ViettelSubscriber2UpdateComponent,
    resolve: {
      viettelSubscriber2: ViettelSubscriber2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ViettelSubscriber2UpdateComponent,
    resolve: {
      viettelSubscriber2: ViettelSubscriber2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(viettelSubscriber2Route)],
  exports: [RouterModule],
})
export class ViettelSubscriber2RoutingModule {}

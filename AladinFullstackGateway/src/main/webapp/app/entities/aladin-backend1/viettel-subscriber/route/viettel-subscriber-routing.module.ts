import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ViettelSubscriberComponent } from '../list/viettel-subscriber.component';
import { ViettelSubscriberDetailComponent } from '../detail/viettel-subscriber-detail.component';
import { ViettelSubscriberUpdateComponent } from '../update/viettel-subscriber-update.component';
import { ViettelSubscriberRoutingResolveService } from './viettel-subscriber-routing-resolve.service';

const viettelSubscriberRoute: Routes = [
  {
    path: '',
    component: ViettelSubscriberComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ViettelSubscriberDetailComponent,
    resolve: {
      viettelSubscriber: ViettelSubscriberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ViettelSubscriberUpdateComponent,
    resolve: {
      viettelSubscriber: ViettelSubscriberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ViettelSubscriberUpdateComponent,
    resolve: {
      viettelSubscriber: ViettelSubscriberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(viettelSubscriberRoute)],
  exports: [RouterModule],
})
export class ViettelSubscriberRoutingModule {}

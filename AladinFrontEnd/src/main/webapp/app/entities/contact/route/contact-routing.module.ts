import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ContactDetailComponent } from '../detail/contact-detail.component';
import { ContactComponent } from '../list/contact.component';
import { ContactRoutingResolveService } from './contact-routing-resolve.service';

const contactRoute: Routes = [
  {
    path: '',
    component: ContactComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContactDetailComponent,
    resolve: {
      contact: ContactRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(contactRoute)],
  exports: [RouterModule],
})
export class ContactRoutingModule {}

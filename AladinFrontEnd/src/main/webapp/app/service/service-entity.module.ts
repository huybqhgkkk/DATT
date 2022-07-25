import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { ServiceListComponent } from './service-list/service-list.component';
import { ServiceDetailComponent } from './service-detail/service-detail.component';
import { ServiceEntityRoutingModule } from './route/service-entity-routing.module';
import { NgxScrollTopModule } from 'ngx-scrolltop';
// export const service_entity_routes: Routes = [
//   {
//     path: 'services',
//     component: ServiceListComponent,
//     data: {
//       pageTitle: 'service.title',
//     },
//   },
//   {
//     path: 'service/:id',
//     component: ServiceDetailComponent,
//   },
// ];

@NgModule({
  imports: [SharedModule, NgxScrollTopModule, ServiceEntityRoutingModule],
  declarations: [ServiceListComponent, ServiceDetailComponent],
})
export class ServiceEntityModule {}

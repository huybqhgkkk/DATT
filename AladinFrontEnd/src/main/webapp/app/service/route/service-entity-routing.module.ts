import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ServiceDetailComponent } from '../service-detail/service-detail.component';
import { ServiceListComponent } from '../service-list/service-list.component';

const service_entity_routes: Routes = [
  {
    path: 'service/:id',
    component: ServiceDetailComponent,
    data: {
      pageTitle: 'AladinTechApp.services.home.title',
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(service_entity_routes)],
  exports: [RouterModule],
})
export class ServiceEntityRoutingModule {}

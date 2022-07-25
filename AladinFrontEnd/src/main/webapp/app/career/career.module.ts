import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { CareerListComponent } from './career-list/career-list.component';
import { CareerDetailComponent } from './career-detail/career-detail.component';
import { NgxScrollTopModule } from 'ngx-scrolltop';
const routes: Routes = [
  {
    path: 'career',
    component: CareerListComponent,
    data: {
      defaultSort: 'id,asc',
      pageTitle: 'AladinTechApp.recruitmen.home.title',
    },
  },
  {
    path: 'career/:id',
    component: CareerDetailComponent,
    data: {
      pageTitle: 'AladinTechApp.recruitmen.home.title',
    },
  },
];

@NgModule({
  imports: [SharedModule, NgxScrollTopModule, RouterModule.forChild(routes)],
  declarations: [CareerListComponent, CareerDetailComponent],
})
export class CareerModule {}

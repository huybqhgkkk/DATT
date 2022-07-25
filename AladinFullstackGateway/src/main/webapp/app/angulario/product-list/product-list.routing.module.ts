import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import {ProductListComponent} from "app/angulario/product-list/product-list.component";

const productListRoute: Routes = [
  {
    path: '',
    component: ProductListComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(productListRoute)],
  exports: [RouterModule],
})
export class ProductListRoutingModule {}

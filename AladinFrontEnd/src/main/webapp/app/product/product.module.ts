import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { ProductRoutingModule } from './route/product-routing.module';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { ListProductComponent } from './list-product/list-product.component';

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
  imports: [SharedModule, ProductRoutingModule],
  declarations: [ProductDetailComponent, ListProductComponent],
})
export class ProductModule {}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListProductComponent } from '../list-product/list-product.component';
import { ProductDetailComponent } from '../product-detail/product-detail.component';

const products_routes: Routes = [
  {
    path: 'products',
    component: ListProductComponent,
  },
  {
    path: 'products/:id',
    component: ProductDetailComponent,
    data: {
      pageTitle: 'AladinTechApp.product.home.title',
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(products_routes)],
  exports: [RouterModule],
})
export class ProductRoutingModule {}

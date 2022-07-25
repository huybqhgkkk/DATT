import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import {ProductListComponent} from "app/angulario/product-list/product-list.component";
import {ProductListRoutingModule} from "app/angulario/product-list/product-list.routing.module";

@NgModule({
  imports: [SharedModule, ProductListRoutingModule],
  declarations: [
    ProductListComponent,
  ],
})
export class ProductListModule {}

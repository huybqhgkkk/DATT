import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'product-list',
        data: { pageTitle: 'aladinFullstackApp.viettelSubscriber.home.title' },
        loadChildren: () => import('./product-list/product-list.module').then(m => m.ProductListModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class AngularioRoutingModule {}

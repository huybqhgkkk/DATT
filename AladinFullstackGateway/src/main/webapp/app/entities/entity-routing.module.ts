import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'viettel-subscriber',
        data: { pageTitle: 'aladinFullstackApp.viettelSubscriber.home.title' },
        loadChildren: () => import('./aladin-backend1/viettel-subscriber/viettel-subscriber.module').then(m => m.ViettelSubscriberModule),
      },
      {
        path: 'heroes',
        data: { pageTitle: 'aladinFullstackApp.heroes.home.title' },
        loadChildren: () => import('./aladin-backend1/heroes/heroes.module').then(m => m.HeroesModule),
      },
      {
        path: 'viettel-subscriber-2',
        data: { pageTitle: 'aladinFullstackApp.viettelSubscriber2.home.title' },
        loadChildren: () => import('./aladin-backend2/viettel-subscriber-2/viettel-subscriber-2.module').then(m => m.ViettelSubscriber2Module),
      },
      {
        path: 'heroes-2',
        data: { pageTitle: 'aladinFullstackApp.heroes2.home.title' },
        loadChildren: () => import('./aladin-backend2/heroes-2/heroes-2.module').then(m => m.Heroes2Module),
      },
      {
        path: 'heroes-3',
        data: { pageTitle: 'aladinFullstackApp.heroes3.home.title' },
        loadChildren: () => import('./aladin-backend2/heroes-3/heroes-3.module').then(m => m.Heroes3Module),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}

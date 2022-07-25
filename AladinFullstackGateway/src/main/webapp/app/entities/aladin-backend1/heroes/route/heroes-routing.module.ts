import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HeroesComponent } from '../list/heroes.component';
import { HeroesDetailComponent } from '../detail/heroes-detail.component';
import { HeroesUpdateComponent } from '../update/heroes-update.component';
import { HeroesRoutingResolveService } from './heroes-routing-resolve.service';

const heroesRoute: Routes = [
  {
    path: '',
    component: HeroesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HeroesDetailComponent,
    resolve: {
      heroes: HeroesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HeroesUpdateComponent,
    resolve: {
      heroes: HeroesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HeroesUpdateComponent,
    resolve: {
      heroes: HeroesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(heroesRoute)],
  exports: [RouterModule],
})
export class HeroesRoutingModule {}

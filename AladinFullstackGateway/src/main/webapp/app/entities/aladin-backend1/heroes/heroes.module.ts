import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { HeroesComponent } from './list/heroes.component';
import { HeroesDetailComponent } from './detail/heroes-detail.component';
import { HeroesUpdateComponent } from './update/heroes-update.component';
import { HeroesDeleteDialogComponent } from './delete/heroes-delete-dialog.component';
import { HeroesRoutingModule } from './route/heroes-routing.module';

@NgModule({
  imports: [SharedModule, HeroesRoutingModule],
  declarations: [HeroesComponent, HeroesDetailComponent, HeroesUpdateComponent, HeroesDeleteDialogComponent],
  entryComponents: [HeroesDeleteDialogComponent],
})
export class HeroesModule {}

import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { Heroes3Component } from './list/heroes-3.component';
import { Heroes3DetailComponent } from './detail/heroes-3-detail.component';
import { Heroes3UpdateComponent } from './update/heroes-3-update.component';
import { Heroes3DeleteDialogComponent } from './delete/heroes-3-delete-dialog.component';
import { Heroes3RoutingModule } from './route/heroes-3-routing.module';

@NgModule({
  imports: [SharedModule, Heroes3RoutingModule],
  declarations: [Heroes3Component, Heroes3DetailComponent, Heroes3UpdateComponent, Heroes3DeleteDialogComponent],
  entryComponents: [Heroes3DeleteDialogComponent],
})
export class Heroes3Module {}

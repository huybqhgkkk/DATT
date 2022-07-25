import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { Heroes2Component } from './list/heroes-2.component';
import { Heroes2DetailComponent } from './detail/heroes-2-detail.component';
import { Heroes2UpdateComponent } from './update/heroes-2-update.component';
import { Heroes2DeleteDialogComponent } from './delete/heroes-2-delete-dialog.component';
import { Heroes2RoutingModule } from './route/heroes-2-routing.module';

@NgModule({
  imports: [SharedModule, Heroes2RoutingModule],
  declarations: [Heroes2Component, Heroes2DetailComponent, Heroes2UpdateComponent, Heroes2DeleteDialogComponent],
  entryComponents: [Heroes2DeleteDialogComponent],
})
export class Heroes2Module {}

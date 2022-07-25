import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ViettelSubscriber2Component } from './list/viettel-subscriber-2.component';
import { ViettelSubscriber2DetailComponent } from './detail/viettel-subscriber-2-detail.component';
import { ViettelSubscriber2UpdateComponent } from './update/viettel-subscriber-2-update.component';
import { ViettelSubscriber2DeleteDialogComponent } from './delete/viettel-subscriber-2-delete-dialog.component';
import { ViettelSubscriber2RoutingModule } from './route/viettel-subscriber-2-routing.module';

@NgModule({
  imports: [SharedModule, ViettelSubscriber2RoutingModule],
  declarations: [
    ViettelSubscriber2Component,
    ViettelSubscriber2DetailComponent,
    ViettelSubscriber2UpdateComponent,
    ViettelSubscriber2DeleteDialogComponent,
  ],
  entryComponents: [ViettelSubscriber2DeleteDialogComponent],
})
export class ViettelSubscriber2Module {}

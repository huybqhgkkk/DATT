import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ViettelSubscriberComponent } from './list/viettel-subscriber.component';
import { ViettelSubscriberDetailComponent } from './detail/viettel-subscriber-detail.component';
import { ViettelSubscriberUpdateComponent } from './update/viettel-subscriber-update.component';
import { ViettelSubscriberDeleteDialogComponent } from './delete/viettel-subscriber-delete-dialog.component';
import { ViettelSubscriberRoutingModule } from './route/viettel-subscriber-routing.module';

@NgModule({
  imports: [SharedModule, ViettelSubscriberRoutingModule],
  declarations: [
    ViettelSubscriberComponent,
    ViettelSubscriberDetailComponent,
    ViettelSubscriberUpdateComponent,
    ViettelSubscriberDeleteDialogComponent,
  ],
  entryComponents: [ViettelSubscriberDeleteDialogComponent],
})
export class ViettelSubscriberModule {}

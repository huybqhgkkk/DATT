import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IViettelSubscriber } from '../viettel-subscriber.model';
import { ViettelSubscriberService } from '../service/viettel-subscriber.service';

@Component({
  templateUrl: './viettel-subscriber-delete-dialog.component.html',
})
export class ViettelSubscriberDeleteDialogComponent {
  viettelSubscriber?: IViettelSubscriber;

  constructor(protected viettelSubscriberService: ViettelSubscriberService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.viettelSubscriberService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}

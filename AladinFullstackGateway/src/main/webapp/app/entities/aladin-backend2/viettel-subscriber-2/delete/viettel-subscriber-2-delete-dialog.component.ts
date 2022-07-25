import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IViettelSubscriber2 } from '../viettel-subscriber-2.model';
import { ViettelSubscriber2Service } from '../service/viettel-subscriber-2.service';

@Component({
  templateUrl: './viettel-subscriber-2-delete-dialog.component.html',
})
export class ViettelSubscriber2DeleteDialogComponent {
  viettelSubscriber2?: IViettelSubscriber2;

  constructor(protected viettelSubscriber2Service: ViettelSubscriber2Service, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.viettelSubscriber2Service.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}

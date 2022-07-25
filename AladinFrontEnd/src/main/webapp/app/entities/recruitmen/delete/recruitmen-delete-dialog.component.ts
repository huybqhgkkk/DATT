import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRecruitmen } from '../recruitmen.model';
import { RecruitmenService } from '../service/recruitmen.service';

@Component({
  templateUrl: './recruitmen-delete-dialog.component.html',
})
export class RecruitmenDeleteDialogComponent {
  recruitmen?: IRecruitmen;

  constructor(protected recruitmenService: RecruitmenService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.recruitmenService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}

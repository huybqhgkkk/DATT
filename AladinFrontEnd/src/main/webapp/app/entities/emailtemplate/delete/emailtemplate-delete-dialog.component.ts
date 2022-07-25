import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmailtemplate } from '../emailtemplate.model';
import { EmailtemplateService } from '../service/emailtemplate.service';

@Component({
  templateUrl: './emailtemplate-delete-dialog.component.html',
})
export class EmailtemplateDeleteDialogComponent {
  emailtemplate?: IEmailtemplate;

  constructor(protected emailtemplateService: EmailtemplateService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emailtemplateService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}

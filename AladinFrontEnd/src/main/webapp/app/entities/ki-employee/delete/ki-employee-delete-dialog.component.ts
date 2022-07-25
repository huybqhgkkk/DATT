import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IKiEmployee } from '../ki-employee.model';
import { KiEmployeeService } from '../service/ki-employee.service';

@Component({
  templateUrl: './ki-employee-delete-dialog.component.html',
})
export class KiEmployeeDeleteDialogComponent {
  kiEmployee?: IKiEmployee;

  constructor(protected kiEmployeeService: KiEmployeeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kiEmployeeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}

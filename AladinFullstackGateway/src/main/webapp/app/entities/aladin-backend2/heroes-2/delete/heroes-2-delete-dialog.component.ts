import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHeroes2 } from '../heroes-2.model';
import { Heroes2Service } from '../service/heroes-2.service';

@Component({
  templateUrl: './heroes-2-delete-dialog.component.html',
})
export class Heroes2DeleteDialogComponent {
  heroes2?: IHeroes2;

  constructor(protected heroes2Service: Heroes2Service, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.heroes2Service.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}

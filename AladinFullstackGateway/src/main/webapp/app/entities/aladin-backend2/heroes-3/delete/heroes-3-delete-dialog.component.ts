import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHeroes3 } from '../heroes-3.model';
import { Heroes3Service } from '../service/heroes-3.service';

@Component({
  templateUrl: './heroes-3-delete-dialog.component.html',
})
export class Heroes3DeleteDialogComponent {
  heroes3?: IHeroes3;

  constructor(protected heroes3Service: Heroes3Service, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.heroes3Service.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}

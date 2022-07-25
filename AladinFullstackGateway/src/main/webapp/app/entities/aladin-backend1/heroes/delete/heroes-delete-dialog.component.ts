import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHeroes } from '../heroes.model';
import { HeroesService } from '../service/heroes.service';

@Component({
  templateUrl: './heroes-delete-dialog.component.html',
})
export class HeroesDeleteDialogComponent {
  heroes?: IHeroes;

  constructor(protected heroesService: HeroesService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.heroesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}

jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Heroes3Service } from '../service/heroes-3.service';

import { Heroes3DeleteDialogComponent } from './heroes-3-delete-dialog.component';

describe('Component Tests', () => {
  describe('Heroes3 Management Delete Component', () => {
    let comp: Heroes3DeleteDialogComponent;
    let fixture: ComponentFixture<Heroes3DeleteDialogComponent>;
    let service: Heroes3Service;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [Heroes3DeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(Heroes3DeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Heroes3DeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(Heroes3Service);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});

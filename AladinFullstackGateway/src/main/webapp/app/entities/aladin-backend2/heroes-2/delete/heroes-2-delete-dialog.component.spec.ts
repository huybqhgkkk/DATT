jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Heroes2Service } from '../service/heroes-2.service';

import { Heroes2DeleteDialogComponent } from './heroes-2-delete-dialog.component';

describe('Component Tests', () => {
  describe('Heroes2 Management Delete Component', () => {
    let comp: Heroes2DeleteDialogComponent;
    let fixture: ComponentFixture<Heroes2DeleteDialogComponent>;
    let service: Heroes2Service;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [Heroes2DeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(Heroes2DeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Heroes2DeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(Heroes2Service);
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

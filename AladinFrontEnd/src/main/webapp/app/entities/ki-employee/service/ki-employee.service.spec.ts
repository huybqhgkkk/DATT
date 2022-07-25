import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IKiEmployee, KiEmployee } from '../ki-employee.model';

import { KiEmployeeService } from './ki-employee.service';

describe('Service Tests', () => {
  describe('KiEmployee Service', () => {
    let service: KiEmployeeService;
    let httpMock: HttpTestingController;
    let elemDefault: IKiEmployee;
    let expectedResult: IKiEmployee | IKiEmployee[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(KiEmployeeService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        date_time: currentDate,
        work_quantity: 0,
        work_quantity_comment: 'AAAAAAA',
        work_quality: 0,
        work_quality_comment: 'AAAAAAA',
        work_progress: 0,
        work_progress_comment: 'AAAAAAA',
        work_attitude: 0,
        work_attitude_comment: 'AAAAAAA',
        work_discipline: 0,
        work_discipline_comment: 'AAAAAAA',
        assigned_work: 'AAAAAAA',
        other_work: 'AAAAAAA',
        completed_work: 'AAAAAAA',
        uncompleted_work: 'AAAAAAA',
        favourite_work: 'AAAAAAA',
        unfavourite_work: 'AAAAAAA',
        employee_ki_point: 0,
        leader_ki_point: 0,
        leader_comment: 'AAAAAAA',
        boss_ki_point: 0,
        boss_comment: 'AAAAAAA',
        status: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            date_time: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a KiEmployee', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date_time: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date_time: currentDate,
          },
          returnedFromService
        );

        service.create(new KiEmployee()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a KiEmployee', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            date_time: currentDate.format(DATE_FORMAT),
            work_quantity: 1,
            work_quantity_comment: 'BBBBBB',
            work_quality: 1,
            work_quality_comment: 'BBBBBB',
            work_progress: 1,
            work_progress_comment: 'BBBBBB',
            work_attitude: 1,
            work_attitude_comment: 'BBBBBB',
            work_discipline: 1,
            work_discipline_comment: 'BBBBBB',
            assigned_work: 'BBBBBB',
            other_work: 'BBBBBB',
            completed_work: 'BBBBBB',
            uncompleted_work: 'BBBBBB',
            favourite_work: 'BBBBBB',
            unfavourite_work: 'BBBBBB',
            employee_ki_point: 1,
            leader_ki_point: 1,
            leader_comment: 'BBBBBB',
            boss_ki_point: 1,
            boss_comment: 'BBBBBB',
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date_time: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a KiEmployee', () => {
        const patchObject = Object.assign(
          {
            date_time: currentDate.format(DATE_FORMAT),
            work_quantity: 1,
            work_progress: 1,
            work_progress_comment: 'BBBBBB',
            work_attitude: 1,
            assigned_work: 'BBBBBB',
            other_work: 'BBBBBB',
            favourite_work: 'BBBBBB',
            leader_comment: 'BBBBBB',
            boss_ki_point: 1,
            status: 'BBBBBB',
          },
          new KiEmployee()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            date_time: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of KiEmployee', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            date_time: currentDate.format(DATE_FORMAT),
            work_quantity: 1,
            work_quantity_comment: 'BBBBBB',
            work_quality: 1,
            work_quality_comment: 'BBBBBB',
            work_progress: 1,
            work_progress_comment: 'BBBBBB',
            work_attitude: 1,
            work_attitude_comment: 'BBBBBB',
            work_discipline: 1,
            work_discipline_comment: 'BBBBBB',
            assigned_work: 'BBBBBB',
            other_work: 'BBBBBB',
            completed_work: 'BBBBBB',
            uncompleted_work: 'BBBBBB',
            favourite_work: 'BBBBBB',
            unfavourite_work: 'BBBBBB',
            employee_ki_point: 1,
            leader_ki_point: 1,
            leader_comment: 'BBBBBB',
            boss_ki_point: 1,
            boss_comment: 'BBBBBB',
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date_time: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a KiEmployee', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addKiEmployeeToCollectionIfMissing', () => {
        it('should add a KiEmployee to an empty array', () => {
          const kiEmployee: IKiEmployee = { id: 123 };
          expectedResult = service.addKiEmployeeToCollectionIfMissing([], kiEmployee);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(kiEmployee);
        });

        it('should not add a KiEmployee to an array that contains it', () => {
          const kiEmployee: IKiEmployee = { id: 123 };
          const kiEmployeeCollection: IKiEmployee[] = [
            {
              ...kiEmployee,
            },
            { id: 456 },
          ];
          expectedResult = service.addKiEmployeeToCollectionIfMissing(kiEmployeeCollection, kiEmployee);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a KiEmployee to an array that doesn't contain it", () => {
          const kiEmployee: IKiEmployee = { id: 123 };
          const kiEmployeeCollection: IKiEmployee[] = [{ id: 456 }];
          expectedResult = service.addKiEmployeeToCollectionIfMissing(kiEmployeeCollection, kiEmployee);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(kiEmployee);
        });

        it('should add only unique KiEmployee to an array', () => {
          const kiEmployeeArray: IKiEmployee[] = [{ id: 123 }, { id: 456 }, { id: 75322 }];
          const kiEmployeeCollection: IKiEmployee[] = [{ id: 123 }];
          expectedResult = service.addKiEmployeeToCollectionIfMissing(kiEmployeeCollection, ...kiEmployeeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const kiEmployee: IKiEmployee = { id: 123 };
          const kiEmployee2: IKiEmployee = { id: 456 };
          expectedResult = service.addKiEmployeeToCollectionIfMissing([], kiEmployee, kiEmployee2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(kiEmployee);
          expect(expectedResult).toContain(kiEmployee2);
        });

        it('should accept null and undefined values', () => {
          const kiEmployee: IKiEmployee = { id: 123 };
          expectedResult = service.addKiEmployeeToCollectionIfMissing([], null, kiEmployee, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(kiEmployee);
        });

        it('should return initial array if no KiEmployee is added', () => {
          const kiEmployeeCollection: IKiEmployee[] = [{ id: 123 }];
          expectedResult = service.addKiEmployeeToCollectionIfMissing(kiEmployeeCollection, undefined, null);
          expect(expectedResult).toEqual(kiEmployeeCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

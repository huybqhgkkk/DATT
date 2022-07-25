import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IHeroes3, Heroes3 } from '../heroes-3.model';

import { Heroes3Service } from './heroes-3.service';

describe('Service Tests', () => {
  describe('Heroes3 Service', () => {
    let service: Heroes3Service;
    let httpMock: HttpTestingController;
    let elemDefault: IHeroes3;
    let expectedResult: IHeroes3 | IHeroes3[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(Heroes3Service);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        create_time: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            create_time: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Heroes3', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            create_time: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            create_time: currentDate,
          },
          returnedFromService
        );

        service.create(new Heroes3()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Heroes3', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            create_time: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            create_time: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Heroes3', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
          },
          new Heroes3()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            create_time: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Heroes3', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            create_time: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            create_time: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Heroes3', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addHeroes3ToCollectionIfMissing', () => {
        it('should add a Heroes3 to an empty array', () => {
          const heroes3: IHeroes3 = { id: 123 };
          expectedResult = service.addHeroes3ToCollectionIfMissing([], heroes3);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(heroes3);
        });

        it('should not add a Heroes3 to an array that contains it', () => {
          const heroes3: IHeroes3 = { id: 123 };
          const heroes3Collection: IHeroes3[] = [
            {
              ...heroes3,
            },
            { id: 456 },
          ];
          expectedResult = service.addHeroes3ToCollectionIfMissing(heroes3Collection, heroes3);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Heroes3 to an array that doesn't contain it", () => {
          const heroes3: IHeroes3 = { id: 123 };
          const heroes3Collection: IHeroes3[] = [{ id: 456 }];
          expectedResult = service.addHeroes3ToCollectionIfMissing(heroes3Collection, heroes3);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(heroes3);
        });

        it('should add only unique Heroes3 to an array', () => {
          const heroes3Array: IHeroes3[] = [{ id: 123 }, { id: 456 }, { id: 39359 }];
          const heroes3Collection: IHeroes3[] = [{ id: 123 }];
          expectedResult = service.addHeroes3ToCollectionIfMissing(heroes3Collection, ...heroes3Array);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const heroes3: IHeroes3 = { id: 123 };
          const heroes32: IHeroes3 = { id: 456 };
          expectedResult = service.addHeroes3ToCollectionIfMissing([], heroes3, heroes32);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(heroes3);
          expect(expectedResult).toContain(heroes32);
        });

        it('should accept null and undefined values', () => {
          const heroes3: IHeroes3 = { id: 123 };
          expectedResult = service.addHeroes3ToCollectionIfMissing([], null, heroes3, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(heroes3);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

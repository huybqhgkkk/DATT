import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IHeroes2, Heroes2 } from '../heroes-2.model';

import { Heroes2Service } from './heroes-2.service';

describe('Service Tests', () => {
  describe('Heroes2 Service', () => {
    let service: Heroes2Service;
    let httpMock: HttpTestingController;
    let elemDefault: IHeroes2;
    let expectedResult: IHeroes2 | IHeroes2[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(Heroes2Service);
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

      it('should create a Heroes2', () => {
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

        service.create(new Heroes2()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Heroes2', () => {
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

      it('should partial update a Heroes2', () => {
        const patchObject = Object.assign(
          {
            create_time: currentDate.format(DATE_TIME_FORMAT),
          },
          new Heroes2()
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

      it('should return a list of Heroes2', () => {
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

      it('should delete a Heroes2', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addHeroes2ToCollectionIfMissing', () => {
        it('should add a Heroes2 to an empty array', () => {
          const heroes2: IHeroes2 = { id: 123 };
          expectedResult = service.addHeroes2ToCollectionIfMissing([], heroes2);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(heroes2);
        });

        it('should not add a Heroes2 to an array that contains it', () => {
          const heroes2: IHeroes2 = { id: 123 };
          const heroes2Collection: IHeroes2[] = [
            {
              ...heroes2,
            },
            { id: 456 },
          ];
          expectedResult = service.addHeroes2ToCollectionIfMissing(heroes2Collection, heroes2);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Heroes2 to an array that doesn't contain it", () => {
          const heroes2: IHeroes2 = { id: 123 };
          const heroes2Collection: IHeroes2[] = [{ id: 456 }];
          expectedResult = service.addHeroes2ToCollectionIfMissing(heroes2Collection, heroes2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(heroes2);
        });

        it('should add only unique Heroes2 to an array', () => {
          const heroes2Array: IHeroes2[] = [{ id: 123 }, { id: 456 }, { id: 9537 }];
          const heroes2Collection: IHeroes2[] = [{ id: 123 }];
          expectedResult = service.addHeroes2ToCollectionIfMissing(heroes2Collection, ...heroes2Array);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const heroes2: IHeroes2 = { id: 123 };
          const heroes22: IHeroes2 = { id: 456 };
          expectedResult = service.addHeroes2ToCollectionIfMissing([], heroes2, heroes22);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(heroes2);
          expect(expectedResult).toContain(heroes22);
        });

        it('should accept null and undefined values', () => {
          const heroes2: IHeroes2 = { id: 123 };
          expectedResult = service.addHeroes2ToCollectionIfMissing([], null, heroes2, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(heroes2);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

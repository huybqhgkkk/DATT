import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IHeroes, Heroes } from '../heroes.model';

import { HeroesService } from './heroes.service';

describe('Service Tests', () => {
  describe('Heroes Service', () => {
    let service: HeroesService;
    let httpMock: HttpTestingController;
    let elemDefault: IHeroes;
    let expectedResult: IHeroes | IHeroes[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(HeroesService);
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

      it('should create a Heroes', () => {
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

        service.create(new Heroes()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Heroes', () => {
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

      it('should partial update a Heroes', () => {
        const patchObject = Object.assign(
          {
            create_time: currentDate.format(DATE_TIME_FORMAT),
          },
          new Heroes()
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

      it('should return a list of Heroes', () => {
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

      it('should delete a Heroes', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addHeroesToCollectionIfMissing', () => {
        it('should add a Heroes to an empty array', () => {
          const heroes: IHeroes = { id: 123 };
          expectedResult = service.addHeroesToCollectionIfMissing([], heroes);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(heroes);
        });

        it('should not add a Heroes to an array that contains it', () => {
          const heroes: IHeroes = { id: 123 };
          const heroesCollection: IHeroes[] = [
            {
              ...heroes,
            },
            { id: 456 },
          ];
          expectedResult = service.addHeroesToCollectionIfMissing(heroesCollection, heroes);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Heroes to an array that doesn't contain it", () => {
          const heroes: IHeroes = { id: 123 };
          const heroesCollection: IHeroes[] = [{ id: 456 }];
          expectedResult = service.addHeroesToCollectionIfMissing(heroesCollection, heroes);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(heroes);
        });

        it('should add only unique Heroes to an array', () => {
          const heroesArray: IHeroes[] = [{ id: 123 }, { id: 456 }, { id: 84823 }];
          const heroesCollection: IHeroes[] = [{ id: 123 }];
          expectedResult = service.addHeroesToCollectionIfMissing(heroesCollection, ...heroesArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const heroes: IHeroes = { id: 123 };
          const heroes2: IHeroes = { id: 456 };
          expectedResult = service.addHeroesToCollectionIfMissing([], heroes, heroes2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(heroes);
          expect(expectedResult).toContain(heroes2);
        });

        it('should accept null and undefined values', () => {
          const heroes: IHeroes = { id: 123 };
          expectedResult = service.addHeroesToCollectionIfMissing([], null, heroes, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(heroes);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

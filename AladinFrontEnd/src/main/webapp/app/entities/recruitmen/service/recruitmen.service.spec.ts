import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRecruitmen, Recruitmen } from '../recruitmen.model';

import { RecruitmenService } from './recruitmen.service';

describe('Service Tests', () => {
  describe('Recruitmen Service', () => {
    let service: RecruitmenService;
    let httpMock: HttpTestingController;
    let elemDefault: IRecruitmen;
    let expectedResult: IRecruitmen | IRecruitmen[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(RecruitmenService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        position: 'AAAAAAA',
        description: 'AAAAAAA',
        require: 'AAAAAAA',
        benefit: 'AAAAAAA',
        amount: 0,
        job: 'AAAAAAA',
        location: 'AAAAAAA',
        duration: 'AAAAAAA',
        level: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Recruitmen', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Recruitmen()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Recruitmen', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            position: 'BBBBBB',
            description: 'BBBBBB',
            require: 'BBBBBB',
            benefit: 'BBBBBB',
            amount: 1,
            job: 'BBBBBB',
            location: 'BBBBBB',
            duration: 'BBBBBB',
            level: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Recruitmen', () => {
        const patchObject = Object.assign(
          {
            position: 'BBBBBB',
            require: 'BBBBBB',
            amount: 1,
            level: 'BBBBBB',
          },
          new Recruitmen()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Recruitmen', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            position: 'BBBBBB',
            description: 'BBBBBB',
            require: 'BBBBBB',
            benefit: 'BBBBBB',
            amount: 1,
            job: 'BBBBBB',
            location: 'BBBBBB',
            duration: 'BBBBBB',
            level: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Recruitmen', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addRecruitmenToCollectionIfMissing', () => {
        it('should add a Recruitmen to an empty array', () => {
          const recruitmen: IRecruitmen = { id: 123 };
          expectedResult = service.addRecruitmenToCollectionIfMissing([], recruitmen);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(recruitmen);
        });

        it('should not add a Recruitmen to an array that contains it', () => {
          const recruitmen: IRecruitmen = { id: 123 };
          const recruitmenCollection: IRecruitmen[] = [
            {
              ...recruitmen,
            },
            { id: 456 },
          ];
          expectedResult = service.addRecruitmenToCollectionIfMissing(recruitmenCollection, recruitmen);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Recruitmen to an array that doesn't contain it", () => {
          const recruitmen: IRecruitmen = { id: 123 };
          const recruitmenCollection: IRecruitmen[] = [{ id: 456 }];
          expectedResult = service.addRecruitmenToCollectionIfMissing(recruitmenCollection, recruitmen);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(recruitmen);
        });

        it('should add only unique Recruitmen to an array', () => {
          const recruitmenArray: IRecruitmen[] = [{ id: 123 }, { id: 456 }, { id: 49531 }];
          const recruitmenCollection: IRecruitmen[] = [{ id: 123 }];
          expectedResult = service.addRecruitmenToCollectionIfMissing(recruitmenCollection, ...recruitmenArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const recruitmen: IRecruitmen = { id: 123 };
          const recruitmen2: IRecruitmen = { id: 456 };
          expectedResult = service.addRecruitmenToCollectionIfMissing([], recruitmen, recruitmen2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(recruitmen);
          expect(expectedResult).toContain(recruitmen2);
        });

        it('should accept null and undefined values', () => {
          const recruitmen: IRecruitmen = { id: 123 };
          expectedResult = service.addRecruitmenToCollectionIfMissing([], null, recruitmen, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(recruitmen);
        });

        it('should return initial array if no Recruitmen is added', () => {
          const recruitmenCollection: IRecruitmen[] = [{ id: 123 }];
          expectedResult = service.addRecruitmenToCollectionIfMissing(recruitmenCollection, undefined, null);
          expect(expectedResult).toEqual(recruitmenCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

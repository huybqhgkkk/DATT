import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICandidate, Candidate } from '../candidate.model';

import { CandidateService } from './candidate.service';

describe('Service Tests', () => {
  describe('Candidate Service', () => {
    let service: CandidateService;
    let httpMock: HttpTestingController;
    let elemDefault: ICandidate;
    let expectedResult: ICandidate | ICandidate[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CandidateService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        phone: 'AAAAAAA',
        email: 'AAAAAAA',
        position: 'AAAAAAA',
        birthday: currentDate,
        location: 'AAAAAAA',
        preference: 'AAAAAAA',
        education: 'AAAAAAA',
        experience: 'AAAAAAA',
        target: 'AAAAAAA',
        ralationship: 'AAAAAAA',
        timeregister: currentDate,
        fullname: 'AAAAAAA',
        sex: 'AAAAAAA',
        cvContentType: 'image/png',
        cv: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            birthday: currentDate.format(DATE_TIME_FORMAT),
            timeregister: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Candidate', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            birthday: currentDate.format(DATE_TIME_FORMAT),
            timeregister: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthday: currentDate,
            timeregister: currentDate,
          },
          returnedFromService
        );

        service.create(new Candidate()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Candidate', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            phone: 'BBBBBB',
            email: 'BBBBBB',
            position: 'BBBBBB',
            birthday: currentDate.format(DATE_TIME_FORMAT),
            location: 'BBBBBB',
            preference: 'BBBBBB',
            education: 'BBBBBB',
            experience: 'BBBBBB',
            target: 'BBBBBB',
            ralationship: 'BBBBBB',
            timeregister: currentDate.format(DATE_TIME_FORMAT),
            fullname: 'BBBBBB',
            sex: 'BBBBBB',
            cv: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthday: currentDate,
            timeregister: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Candidate', () => {
        const patchObject = Object.assign(
          {
            phone: 'BBBBBB',
            position: 'BBBBBB',
            location: 'BBBBBB',
            experience: 'BBBBBB',
            ralationship: 'BBBBBB',
            cv: 'BBBBBB',
          },
          new Candidate()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            birthday: currentDate,
            timeregister: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Candidate', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            phone: 'BBBBBB',
            email: 'BBBBBB',
            position: 'BBBBBB',
            birthday: currentDate.format(DATE_TIME_FORMAT),
            location: 'BBBBBB',
            preference: 'BBBBBB',
            education: 'BBBBBB',
            experience: 'BBBBBB',
            target: 'BBBBBB',
            ralationship: 'BBBBBB',
            timeregister: currentDate.format(DATE_TIME_FORMAT),
            fullname: 'BBBBBB',
            sex: 'BBBBBB',
            cv: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthday: currentDate,
            timeregister: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Candidate', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCandidateToCollectionIfMissing', () => {
        it('should add a Candidate to an empty array', () => {
          const candidate: ICandidate = { id: 123 };
          expectedResult = service.addCandidateToCollectionIfMissing([], candidate);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(candidate);
        });

        it('should not add a Candidate to an array that contains it', () => {
          const candidate: ICandidate = { id: 123 };
          const candidateCollection: ICandidate[] = [
            {
              ...candidate,
            },
            { id: 456 },
          ];
          expectedResult = service.addCandidateToCollectionIfMissing(candidateCollection, candidate);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Candidate to an array that doesn't contain it", () => {
          const candidate: ICandidate = { id: 123 };
          const candidateCollection: ICandidate[] = [{ id: 456 }];
          expectedResult = service.addCandidateToCollectionIfMissing(candidateCollection, candidate);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(candidate);
        });

        it('should add only unique Candidate to an array', () => {
          const candidateArray: ICandidate[] = [{ id: 123 }, { id: 456 }, { id: 49184 }];
          const candidateCollection: ICandidate[] = [{ id: 123 }];
          expectedResult = service.addCandidateToCollectionIfMissing(candidateCollection, ...candidateArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const candidate: ICandidate = { id: 123 };
          const candidate2: ICandidate = { id: 456 };
          expectedResult = service.addCandidateToCollectionIfMissing([], candidate, candidate2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(candidate);
          expect(expectedResult).toContain(candidate2);
        });

        it('should accept null and undefined values', () => {
          const candidate: ICandidate = { id: 123 };
          expectedResult = service.addCandidateToCollectionIfMissing([], null, candidate, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(candidate);
        });

        it('should return initial array if no Candidate is added', () => {
          const candidateCollection: ICandidate[] = [{ id: 123 }];
          expectedResult = service.addCandidateToCollectionIfMissing(candidateCollection, undefined, null);
          expect(expectedResult).toEqual(candidateCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

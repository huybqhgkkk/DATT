import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IViettelSubscriber, ViettelSubscriber } from '../viettel-subscriber.model';

import { ViettelSubscriberService } from './viettel-subscriber.service';

describe('Service Tests', () => {
  describe('ViettelSubscriber Service', () => {
    let service: ViettelSubscriberService;
    let httpMock: HttpTestingController;
    let elemDefault: IViettelSubscriber;
    let expectedResult: IViettelSubscriber | IViettelSubscriber[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ViettelSubscriberService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        msisdn: 'AAAAAAA',
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

      it('should create a ViettelSubscriber', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ViettelSubscriber()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ViettelSubscriber', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            msisdn: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ViettelSubscriber', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
          },
          new ViettelSubscriber()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ViettelSubscriber', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            msisdn: 'BBBBBB',
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

      it('should delete a ViettelSubscriber', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addViettelSubscriberToCollectionIfMissing', () => {
        it('should add a ViettelSubscriber to an empty array', () => {
          const viettelSubscriber: IViettelSubscriber = { id: 123 };
          expectedResult = service.addViettelSubscriberToCollectionIfMissing([], viettelSubscriber);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(viettelSubscriber);
        });

        it('should not add a ViettelSubscriber to an array that contains it', () => {
          const viettelSubscriber: IViettelSubscriber = { id: 123 };
          const viettelSubscriberCollection: IViettelSubscriber[] = [
            {
              ...viettelSubscriber,
            },
            { id: 456 },
          ];
          expectedResult = service.addViettelSubscriberToCollectionIfMissing(viettelSubscriberCollection, viettelSubscriber);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ViettelSubscriber to an array that doesn't contain it", () => {
          const viettelSubscriber: IViettelSubscriber = { id: 123 };
          const viettelSubscriberCollection: IViettelSubscriber[] = [{ id: 456 }];
          expectedResult = service.addViettelSubscriberToCollectionIfMissing(viettelSubscriberCollection, viettelSubscriber);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(viettelSubscriber);
        });

        it('should add only unique ViettelSubscriber to an array', () => {
          const viettelSubscriberArray: IViettelSubscriber[] = [{ id: 123 }, { id: 456 }, { id: 26365 }];
          const viettelSubscriberCollection: IViettelSubscriber[] = [{ id: 123 }];
          expectedResult = service.addViettelSubscriberToCollectionIfMissing(viettelSubscriberCollection, ...viettelSubscriberArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const viettelSubscriber: IViettelSubscriber = { id: 123 };
          const viettelSubscriber2: IViettelSubscriber = { id: 456 };
          expectedResult = service.addViettelSubscriberToCollectionIfMissing([], viettelSubscriber, viettelSubscriber2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(viettelSubscriber);
          expect(expectedResult).toContain(viettelSubscriber2);
        });

        it('should accept null and undefined values', () => {
          const viettelSubscriber: IViettelSubscriber = { id: 123 };
          expectedResult = service.addViettelSubscriberToCollectionIfMissing([], null, viettelSubscriber, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(viettelSubscriber);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

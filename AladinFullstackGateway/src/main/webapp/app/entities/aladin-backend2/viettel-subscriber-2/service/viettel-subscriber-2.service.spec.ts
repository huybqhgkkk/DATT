import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IViettelSubscriber2, ViettelSubscriber2 } from '../viettel-subscriber-2.model';

import { ViettelSubscriber2Service } from './viettel-subscriber-2.service';

describe('Service Tests', () => {
  describe('ViettelSubscriber2 Service', () => {
    let service: ViettelSubscriber2Service;
    let httpMock: HttpTestingController;
    let elemDefault: IViettelSubscriber2;
    let expectedResult: IViettelSubscriber2 | IViettelSubscriber2[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ViettelSubscriber2Service);
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

      it('should create a ViettelSubscriber2', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ViettelSubscriber2()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ViettelSubscriber2', () => {
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

      it('should partial update a ViettelSubscriber2', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
            msisdn: 'BBBBBB',
          },
          new ViettelSubscriber2()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ViettelSubscriber2', () => {
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

      it('should delete a ViettelSubscriber2', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addViettelSubscriber2ToCollectionIfMissing', () => {
        it('should add a ViettelSubscriber2 to an empty array', () => {
          const viettelSubscriber2: IViettelSubscriber2 = { id: 123 };
          expectedResult = service.addViettelSubscriber2ToCollectionIfMissing([], viettelSubscriber2);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(viettelSubscriber2);
        });

        it('should not add a ViettelSubscriber2 to an array that contains it', () => {
          const viettelSubscriber2: IViettelSubscriber2 = { id: 123 };
          const viettelSubscriber2Collection: IViettelSubscriber2[] = [
            {
              ...viettelSubscriber2,
            },
            { id: 456 },
          ];
          expectedResult = service.addViettelSubscriber2ToCollectionIfMissing(viettelSubscriber2Collection, viettelSubscriber2);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ViettelSubscriber2 to an array that doesn't contain it", () => {
          const viettelSubscriber2: IViettelSubscriber2 = { id: 123 };
          const viettelSubscriber2Collection: IViettelSubscriber2[] = [{ id: 456 }];
          expectedResult = service.addViettelSubscriber2ToCollectionIfMissing(viettelSubscriber2Collection, viettelSubscriber2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(viettelSubscriber2);
        });

        it('should add only unique ViettelSubscriber2 to an array', () => {
          const viettelSubscriber2Array: IViettelSubscriber2[] = [{ id: 123 }, { id: 456 }, { id: 272 }];
          const viettelSubscriber2Collection: IViettelSubscriber2[] = [{ id: 123 }];
          expectedResult = service.addViettelSubscriber2ToCollectionIfMissing(viettelSubscriber2Collection, ...viettelSubscriber2Array);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const viettelSubscriber2: IViettelSubscriber2 = { id: 123 };
          const viettelSubscriber22: IViettelSubscriber2 = { id: 456 };
          expectedResult = service.addViettelSubscriber2ToCollectionIfMissing([], viettelSubscriber2, viettelSubscriber22);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(viettelSubscriber2);
          expect(expectedResult).toContain(viettelSubscriber22);
        });

        it('should accept null and undefined values', () => {
          const viettelSubscriber2: IViettelSubscriber2 = { id: 123 };
          expectedResult = service.addViettelSubscriber2ToCollectionIfMissing([], null, viettelSubscriber2, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(viettelSubscriber2);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

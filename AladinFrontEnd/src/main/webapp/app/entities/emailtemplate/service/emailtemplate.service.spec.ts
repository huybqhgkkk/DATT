import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEmailtemplate, Emailtemplate } from '../emailtemplate.model';

import { EmailtemplateService } from './emailtemplate.service';

describe('Emailtemplate Service', () => {
  let service: EmailtemplateService;
  let httpMock: HttpTestingController;
  let elemDefault: IEmailtemplate;
  let expectedResult: IEmailtemplate | IEmailtemplate[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EmailtemplateService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      templatename: 'AAAAAAA',
      subject: 'AAAAAAA',
      hyperlink: 'AAAAAAA',
      datetime: currentDate,
      content: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          datetime: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Emailtemplate', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          datetime: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          datetime: currentDate,
        },
        returnedFromService
      );

      service.create(new Emailtemplate()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Emailtemplate', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          templatename: 'BBBBBB',
          subject: 'BBBBBB',
          hyperlink: 'BBBBBB',
          datetime: currentDate.format(DATE_TIME_FORMAT),
          content: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          datetime: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Emailtemplate', () => {
      const patchObject = Object.assign(
        {
          subject: 'BBBBBB',
          content: 'BBBBBB',
        },
        new Emailtemplate()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          datetime: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Emailtemplate', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          templatename: 'BBBBBB',
          subject: 'BBBBBB',
          hyperlink: 'BBBBBB',
          datetime: currentDate.format(DATE_TIME_FORMAT),
          content: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          datetime: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Emailtemplate', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEmailtemplateToCollectionIfMissing', () => {
      it('should add a Emailtemplate to an empty array', () => {
        const emailtemplate: IEmailtemplate = { id: 123 };
        expectedResult = service.addEmailtemplateToCollectionIfMissing([], emailtemplate);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(emailtemplate);
      });

      it('should not add a Emailtemplate to an array that contains it', () => {
        const emailtemplate: IEmailtemplate = { id: 123 };
        const emailtemplateCollection: IEmailtemplate[] = [
          {
            ...emailtemplate,
          },
          { id: 456 },
        ];
        expectedResult = service.addEmailtemplateToCollectionIfMissing(emailtemplateCollection, emailtemplate);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Emailtemplate to an array that doesn't contain it", () => {
        const emailtemplate: IEmailtemplate = { id: 123 };
        const emailtemplateCollection: IEmailtemplate[] = [{ id: 456 }];
        expectedResult = service.addEmailtemplateToCollectionIfMissing(emailtemplateCollection, emailtemplate);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(emailtemplate);
      });

      it('should add only unique Emailtemplate to an array', () => {
        const emailtemplateArray: IEmailtemplate[] = [{ id: 123 }, { id: 456 }, { id: 31945 }];
        const emailtemplateCollection: IEmailtemplate[] = [{ id: 123 }];
        expectedResult = service.addEmailtemplateToCollectionIfMissing(emailtemplateCollection, ...emailtemplateArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const emailtemplate: IEmailtemplate = { id: 123 };
        const emailtemplate2: IEmailtemplate = { id: 456 };
        expectedResult = service.addEmailtemplateToCollectionIfMissing([], emailtemplate, emailtemplate2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(emailtemplate);
        expect(expectedResult).toContain(emailtemplate2);
      });

      it('should accept null and undefined values', () => {
        const emailtemplate: IEmailtemplate = { id: 123 };
        expectedResult = service.addEmailtemplateToCollectionIfMissing([], null, emailtemplate, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(emailtemplate);
      });

      it('should return initial array if no Emailtemplate is added', () => {
        const emailtemplateCollection: IEmailtemplate[] = [{ id: 123 }];
        expectedResult = service.addEmailtemplateToCollectionIfMissing(emailtemplateCollection, undefined, null);
        expect(expectedResult).toEqual(emailtemplateCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

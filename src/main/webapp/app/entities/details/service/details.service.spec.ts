import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDetails, Details } from '../details.model';

import { DetailsService } from './details.service';

describe('Details Service', () => {
  let service: DetailsService;
  let httpMock: HttpTestingController;
  let elemDefault: IDetails;
  let expectedResult: IDetails | IDetails[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DetailsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 'AAAAAAA',
      loginName: 'AAAAAAA',
      password: 'AAAAAAA',
      lastName: 'AAAAAAA',
      firstName: 'AAAAAAA',
      securtiyQuestion: 'AAAAAAA',
      securityAnswer: 'AAAAAAA',
      parentalPassword: 'AAAAAAA',
      firstCdKey: 'AAAAAAA',
      otherInformation: 'AAAAAAA',
      enteredDate: currentDate,
      orderDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          enteredDate: currentDate.format(DATE_TIME_FORMAT),
          orderDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Details', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
          enteredDate: currentDate.format(DATE_TIME_FORMAT),
          orderDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          enteredDate: currentDate,
          orderDate: currentDate,
        },
        returnedFromService
      );

      service.create(new Details()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Details', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          loginName: 'BBBBBB',
          password: 'BBBBBB',
          lastName: 'BBBBBB',
          firstName: 'BBBBBB',
          securtiyQuestion: 'BBBBBB',
          securityAnswer: 'BBBBBB',
          parentalPassword: 'BBBBBB',
          firstCdKey: 'BBBBBB',
          otherInformation: 'BBBBBB',
          enteredDate: currentDate.format(DATE_TIME_FORMAT),
          orderDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          enteredDate: currentDate,
          orderDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Details', () => {
      const patchObject = Object.assign(
        {
          password: 'BBBBBB',
          lastName: 'BBBBBB',
          securtiyQuestion: 'BBBBBB',
          securityAnswer: 'BBBBBB',
          enteredDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new Details()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          enteredDate: currentDate,
          orderDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Details', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          loginName: 'BBBBBB',
          password: 'BBBBBB',
          lastName: 'BBBBBB',
          firstName: 'BBBBBB',
          securtiyQuestion: 'BBBBBB',
          securityAnswer: 'BBBBBB',
          parentalPassword: 'BBBBBB',
          firstCdKey: 'BBBBBB',
          otherInformation: 'BBBBBB',
          enteredDate: currentDate.format(DATE_TIME_FORMAT),
          orderDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          enteredDate: currentDate,
          orderDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Details', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDetailsToCollectionIfMissing', () => {
      it('should add a Details to an empty array', () => {
        const details: IDetails = { id: 'ABC' };
        expectedResult = service.addDetailsToCollectionIfMissing([], details);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(details);
      });

      it('should not add a Details to an array that contains it', () => {
        const details: IDetails = { id: 'ABC' };
        const detailsCollection: IDetails[] = [
          {
            ...details,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addDetailsToCollectionIfMissing(detailsCollection, details);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Details to an array that doesn't contain it", () => {
        const details: IDetails = { id: 'ABC' };
        const detailsCollection: IDetails[] = [{ id: 'CBA' }];
        expectedResult = service.addDetailsToCollectionIfMissing(detailsCollection, details);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(details);
      });

      it('should add only unique Details to an array', () => {
        const detailsArray: IDetails[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: '26359020-75d1-417c-a1af-9e1a81d42035' }];
        const detailsCollection: IDetails[] = [{ id: 'ABC' }];
        expectedResult = service.addDetailsToCollectionIfMissing(detailsCollection, ...detailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const details: IDetails = { id: 'ABC' };
        const details2: IDetails = { id: 'CBA' };
        expectedResult = service.addDetailsToCollectionIfMissing([], details, details2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(details);
        expect(expectedResult).toContain(details2);
      });

      it('should accept null and undefined values', () => {
        const details: IDetails = { id: 'ABC' };
        expectedResult = service.addDetailsToCollectionIfMissing([], null, details, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(details);
      });

      it('should return initial array if no Details is added', () => {
        const detailsCollection: IDetails[] = [{ id: 'ABC' }];
        expectedResult = service.addDetailsToCollectionIfMissing(detailsCollection, undefined, null);
        expect(expectedResult).toEqual(detailsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

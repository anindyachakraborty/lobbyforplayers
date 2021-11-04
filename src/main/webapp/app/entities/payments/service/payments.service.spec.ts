import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPayments, Payments } from '../payments.model';

import { PaymentsService } from './payments.service';

describe('Payments Service', () => {
  let service: PaymentsService;
  let httpMock: HttpTestingController;
  let elemDefault: IPayments;
  let expectedResult: IPayments | IPayments[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PaymentsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 'AAAAAAA',
      buyerUserId: 'AAAAAAA',
      sellerUserId: 'AAAAAAA',
      sellerRecieved: false,
      buyerTransactionId: 'AAAAAAA',
      sellerTransactionId: 'AAAAAAA',
      orderId: 'AAAAAAA',
      packageId: 'AAAAAAA',
      amount: 0,
      game: 'AAAAAAA',
      status: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Payments', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Payments()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Payments', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          buyerUserId: 'BBBBBB',
          sellerUserId: 'BBBBBB',
          sellerRecieved: true,
          buyerTransactionId: 'BBBBBB',
          sellerTransactionId: 'BBBBBB',
          orderId: 'BBBBBB',
          packageId: 'BBBBBB',
          amount: 1,
          game: 'BBBBBB',
          status: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Payments', () => {
      const patchObject = Object.assign(
        {
          sellerUserId: 'BBBBBB',
          sellerTransactionId: 'BBBBBB',
          orderId: 'BBBBBB',
          game: 'BBBBBB',
        },
        new Payments()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Payments', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          buyerUserId: 'BBBBBB',
          sellerUserId: 'BBBBBB',
          sellerRecieved: true,
          buyerTransactionId: 'BBBBBB',
          sellerTransactionId: 'BBBBBB',
          orderId: 'BBBBBB',
          packageId: 'BBBBBB',
          amount: 1,
          game: 'BBBBBB',
          status: 'BBBBBB',
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

    it('should delete a Payments', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPaymentsToCollectionIfMissing', () => {
      it('should add a Payments to an empty array', () => {
        const payments: IPayments = { id: 'ABC' };
        expectedResult = service.addPaymentsToCollectionIfMissing([], payments);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(payments);
      });

      it('should not add a Payments to an array that contains it', () => {
        const payments: IPayments = { id: 'ABC' };
        const paymentsCollection: IPayments[] = [
          {
            ...payments,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addPaymentsToCollectionIfMissing(paymentsCollection, payments);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Payments to an array that doesn't contain it", () => {
        const payments: IPayments = { id: 'ABC' };
        const paymentsCollection: IPayments[] = [{ id: 'CBA' }];
        expectedResult = service.addPaymentsToCollectionIfMissing(paymentsCollection, payments);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(payments);
      });

      it('should add only unique Payments to an array', () => {
        const paymentsArray: IPayments[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: '09118d83-4cc2-41e8-a602-968aba64e09f' }];
        const paymentsCollection: IPayments[] = [{ id: 'ABC' }];
        expectedResult = service.addPaymentsToCollectionIfMissing(paymentsCollection, ...paymentsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const payments: IPayments = { id: 'ABC' };
        const payments2: IPayments = { id: 'CBA' };
        expectedResult = service.addPaymentsToCollectionIfMissing([], payments, payments2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(payments);
        expect(expectedResult).toContain(payments2);
      });

      it('should accept null and undefined values', () => {
        const payments: IPayments = { id: 'ABC' };
        expectedResult = service.addPaymentsToCollectionIfMissing([], null, payments, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(payments);
      });

      it('should return initial array if no Payments is added', () => {
        const paymentsCollection: IPayments[] = [{ id: 'ABC' }];
        expectedResult = service.addPaymentsToCollectionIfMissing(paymentsCollection, undefined, null);
        expect(expectedResult).toEqual(paymentsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

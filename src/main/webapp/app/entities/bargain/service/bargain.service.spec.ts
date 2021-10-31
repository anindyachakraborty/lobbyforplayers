import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBargain, Bargain } from '../bargain.model';

import { BargainService } from './bargain.service';

describe('Bargain Service', () => {
  let service: BargainService;
  let httpMock: HttpTestingController;
  let elemDefault: IBargain;
  let expectedResult: IBargain | IBargain[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BargainService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      bargainPrice: 0,
      itemId: 'AAAAAAA',
      sellerApproved: false,
      buyerApproved: false,
      sellerId: 'AAAAAAA',
      buyerId: 'AAAAAAA',
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

    it('should create a Bargain', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Bargain()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Bargain', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          bargainPrice: 1,
          itemId: 'BBBBBB',
          sellerApproved: true,
          buyerApproved: true,
          sellerId: 'BBBBBB',
          buyerId: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Bargain', () => {
      const patchObject = Object.assign(
        {
          buyerApproved: true,
          buyerId: 'BBBBBB',
        },
        new Bargain()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Bargain', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          bargainPrice: 1,
          itemId: 'BBBBBB',
          sellerApproved: true,
          buyerApproved: true,
          sellerId: 'BBBBBB',
          buyerId: 'BBBBBB',
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

    it('should delete a Bargain', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBargainToCollectionIfMissing', () => {
      it('should add a Bargain to an empty array', () => {
        const bargain: IBargain = { id: 123 };
        expectedResult = service.addBargainToCollectionIfMissing([], bargain);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bargain);
      });

      it('should not add a Bargain to an array that contains it', () => {
        const bargain: IBargain = { id: 123 };
        const bargainCollection: IBargain[] = [
          {
            ...bargain,
          },
          { id: 456 },
        ];
        expectedResult = service.addBargainToCollectionIfMissing(bargainCollection, bargain);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Bargain to an array that doesn't contain it", () => {
        const bargain: IBargain = { id: 123 };
        const bargainCollection: IBargain[] = [{ id: 456 }];
        expectedResult = service.addBargainToCollectionIfMissing(bargainCollection, bargain);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bargain);
      });

      it('should add only unique Bargain to an array', () => {
        const bargainArray: IBargain[] = [{ id: 123 }, { id: 456 }, { id: 97819 }];
        const bargainCollection: IBargain[] = [{ id: 123 }];
        expectedResult = service.addBargainToCollectionIfMissing(bargainCollection, ...bargainArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bargain: IBargain = { id: 123 };
        const bargain2: IBargain = { id: 456 };
        expectedResult = service.addBargainToCollectionIfMissing([], bargain, bargain2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bargain);
        expect(expectedResult).toContain(bargain2);
      });

      it('should accept null and undefined values', () => {
        const bargain: IBargain = { id: 123 };
        expectedResult = service.addBargainToCollectionIfMissing([], null, bargain, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bargain);
      });

      it('should return initial array if no Bargain is added', () => {
        const bargainCollection: IBargain[] = [{ id: 123 }];
        expectedResult = service.addBargainToCollectionIfMissing(bargainCollection, undefined, null);
        expect(expectedResult).toEqual(bargainCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IReviews, Reviews } from '../reviews.model';

import { ReviewsService } from './reviews.service';

describe('Reviews Service', () => {
  let service: ReviewsService;
  let httpMock: HttpTestingController;
  let elemDefault: IReviews;
  let expectedResult: IReviews | IReviews[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ReviewsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 'AAAAAAA',
      question: 'AAAAAAA',
      rating: 0,
      username: 'AAAAAAA',
      orderId: 'AAAAAAA',
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

    it('should create a Reviews', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Reviews()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Reviews', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          question: 'BBBBBB',
          rating: 1,
          username: 'BBBBBB',
          orderId: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Reviews', () => {
      const patchObject = Object.assign({}, new Reviews());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Reviews', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          question: 'BBBBBB',
          rating: 1,
          username: 'BBBBBB',
          orderId: 'BBBBBB',
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

    it('should delete a Reviews', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addReviewsToCollectionIfMissing', () => {
      it('should add a Reviews to an empty array', () => {
        const reviews: IReviews = { id: 'ABC' };
        expectedResult = service.addReviewsToCollectionIfMissing([], reviews);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(reviews);
      });

      it('should not add a Reviews to an array that contains it', () => {
        const reviews: IReviews = { id: 'ABC' };
        const reviewsCollection: IReviews[] = [
          {
            ...reviews,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addReviewsToCollectionIfMissing(reviewsCollection, reviews);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Reviews to an array that doesn't contain it", () => {
        const reviews: IReviews = { id: 'ABC' };
        const reviewsCollection: IReviews[] = [{ id: 'CBA' }];
        expectedResult = service.addReviewsToCollectionIfMissing(reviewsCollection, reviews);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(reviews);
      });

      it('should add only unique Reviews to an array', () => {
        const reviewsArray: IReviews[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: '2ada283d-29db-4982-b928-17ede127c1ac' }];
        const reviewsCollection: IReviews[] = [{ id: 'ABC' }];
        expectedResult = service.addReviewsToCollectionIfMissing(reviewsCollection, ...reviewsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const reviews: IReviews = { id: 'ABC' };
        const reviews2: IReviews = { id: 'CBA' };
        expectedResult = service.addReviewsToCollectionIfMissing([], reviews, reviews2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(reviews);
        expect(expectedResult).toContain(reviews2);
      });

      it('should accept null and undefined values', () => {
        const reviews: IReviews = { id: 'ABC' };
        expectedResult = service.addReviewsToCollectionIfMissing([], null, reviews, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(reviews);
      });

      it('should return initial array if no Reviews is added', () => {
        const reviewsCollection: IReviews[] = [{ id: 'ABC' }];
        expectedResult = service.addReviewsToCollectionIfMissing(reviewsCollection, undefined, null);
        expect(expectedResult).toEqual(reviewsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

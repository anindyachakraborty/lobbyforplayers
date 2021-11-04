jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IReviews, Reviews } from '../reviews.model';
import { ReviewsService } from '../service/reviews.service';

import { ReviewsRoutingResolveService } from './reviews-routing-resolve.service';

describe('Reviews routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ReviewsRoutingResolveService;
  let service: ReviewsService;
  let resultReviews: IReviews | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(ReviewsRoutingResolveService);
    service = TestBed.inject(ReviewsService);
    resultReviews = undefined;
  });

  describe('resolve', () => {
    it('should return IReviews returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultReviews = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultReviews).toEqual({ id: 'ABC' });
    });

    it('should return new IReviews if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultReviews = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultReviews).toEqual(new Reviews());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Reviews })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultReviews = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultReviews).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

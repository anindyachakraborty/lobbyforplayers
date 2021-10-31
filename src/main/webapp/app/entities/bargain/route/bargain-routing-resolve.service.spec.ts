jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IBargain, Bargain } from '../bargain.model';
import { BargainService } from '../service/bargain.service';

import { BargainRoutingResolveService } from './bargain-routing-resolve.service';

describe('Bargain routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: BargainRoutingResolveService;
  let service: BargainService;
  let resultBargain: IBargain | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(BargainRoutingResolveService);
    service = TestBed.inject(BargainService);
    resultBargain = undefined;
  });

  describe('resolve', () => {
    it('should return IBargain returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBargain = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBargain).toEqual({ id: 123 });
    });

    it('should return new IBargain if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBargain = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultBargain).toEqual(new Bargain());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Bargain })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBargain = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBargain).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

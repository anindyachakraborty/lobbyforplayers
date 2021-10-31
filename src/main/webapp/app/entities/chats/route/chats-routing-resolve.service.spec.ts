jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IChats, Chats } from '../chats.model';
import { ChatsService } from '../service/chats.service';

import { ChatsRoutingResolveService } from './chats-routing-resolve.service';

describe('Chats routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ChatsRoutingResolveService;
  let service: ChatsService;
  let resultChats: IChats | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(ChatsRoutingResolveService);
    service = TestBed.inject(ChatsService);
    resultChats = undefined;
  });

  describe('resolve', () => {
    it('should return IChats returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultChats = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultChats).toEqual({ id: 123 });
    });

    it('should return new IChats if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultChats = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultChats).toEqual(new Chats());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Chats })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultChats = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultChats).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

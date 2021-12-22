jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IQuestionary, Questionary } from '../questionary.model';
import { QuestionaryService } from '../service/questionary.service';

import { QuestionaryRoutingResolveService } from './questionary-routing-resolve.service';

describe('Questionary routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: QuestionaryRoutingResolveService;
  let service: QuestionaryService;
  let resultQuestionary: IQuestionary | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(QuestionaryRoutingResolveService);
    service = TestBed.inject(QuestionaryService);
    resultQuestionary = undefined;
  });

  describe('resolve', () => {
    it('should return IQuestionary returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultQuestionary = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultQuestionary).toEqual({ id: 'ABC' });
    });

    it('should return new IQuestionary if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultQuestionary = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultQuestionary).toEqual(new Questionary());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Questionary })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultQuestionary = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultQuestionary).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IReviews, Reviews } from '../reviews.model';
import { ReviewsService } from '../service/reviews.service';

@Injectable({ providedIn: 'root' })
export class ReviewsRoutingResolveService implements Resolve<IReviews> {
  constructor(protected service: ReviewsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReviews> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((reviews: HttpResponse<Reviews>) => {
          if (reviews.body) {
            return of(reviews.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Reviews());
  }
}

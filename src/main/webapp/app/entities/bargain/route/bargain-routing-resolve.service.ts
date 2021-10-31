import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBargain, Bargain } from '../bargain.model';
import { BargainService } from '../service/bargain.service';

@Injectable({ providedIn: 'root' })
export class BargainRoutingResolveService implements Resolve<IBargain> {
  constructor(protected service: BargainService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBargain> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bargain: HttpResponse<Bargain>) => {
          if (bargain.body) {
            return of(bargain.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Bargain());
  }
}

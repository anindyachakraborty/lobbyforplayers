import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPayments, Payments } from '../payments.model';
import { PaymentsService } from '../service/payments.service';

@Injectable({ providedIn: 'root' })
export class PaymentsRoutingResolveService implements Resolve<IPayments> {
  constructor(protected service: PaymentsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPayments> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((payments: HttpResponse<Payments>) => {
          if (payments.body) {
            return of(payments.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Payments());
  }
}

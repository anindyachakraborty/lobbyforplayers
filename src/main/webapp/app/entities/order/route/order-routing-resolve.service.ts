import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrder, Order } from '../order.model';
import { OrderService } from '../service/order.service';
import { Item } from 'app/entities/item/item.model';
import { Bargain } from 'app/entities/bargain/bargain.model';
import { ItemService } from 'app/entities/item/service/item.service';

@Injectable({ providedIn: 'root' })
export class OrderRoutingResolveService implements Resolve<IOrder> {
  constructor(protected service: OrderService, protected router: Router, protected itemService: ItemService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((order: HttpResponse<Order>) => {
          if (order.body) {
            return of(order.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Order());
  }

  orderNew(route: ActivatedRouteSnapshot): Observable<IOrder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.itemService.find(id).pipe(
        mergeMap((items: HttpResponse<Item>) => {
          if (items.body) {
            let price = items.body.price;
            const bargains: Bargain[] = items.body.bargains ?? [];
            if (bargains.length !== 0) {
              for (const bargain of bargains) {
                if (bargain.itemId === id && bargain.sellerApproved === true && bargain.buyerApproved === true) {
                  price = bargain.bargainPrice;
                }
              }
            }
            return of(new Order(undefined, items.body.sellerName, '', price, '', false, items.body));
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Order());
  }
}

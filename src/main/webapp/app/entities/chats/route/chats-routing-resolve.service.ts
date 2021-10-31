import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IChats, Chats } from '../chats.model';
import { ChatsService } from '../service/chats.service';

@Injectable({ providedIn: 'root' })
export class ChatsRoutingResolveService implements Resolve<IChats> {
  constructor(protected service: ChatsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChats> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((chats: HttpResponse<Chats>) => {
          if (chats.body) {
            return of(chats.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Chats());
  }
}

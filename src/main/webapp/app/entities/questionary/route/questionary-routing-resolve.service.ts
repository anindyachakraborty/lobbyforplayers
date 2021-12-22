import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IQuestionary, Questionary } from '../questionary.model';
import { QuestionaryService } from '../service/questionary.service';

@Injectable({ providedIn: 'root' })
export class QuestionaryRoutingResolveService implements Resolve<IQuestionary> {
  constructor(protected service: QuestionaryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuestionary> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((questionary: HttpResponse<Questionary>) => {
          if (questionary.body) {
            return of(questionary.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Questionary());
  }
}

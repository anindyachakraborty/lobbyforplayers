import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { QuestionaryComponent } from '../list/questionary.component';
import { QuestionaryDetailComponent } from '../detail/questionary-detail.component';
import { QuestionaryUpdateComponent } from '../update/questionary-update.component';
import { QuestionaryRoutingResolveService } from './questionary-routing-resolve.service';

const questionaryRoute: Routes = [
  {
    path: '',
    component: QuestionaryComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QuestionaryDetailComponent,
    resolve: {
      questionary: QuestionaryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QuestionaryUpdateComponent,
    resolve: {
      questionary: QuestionaryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QuestionaryUpdateComponent,
    resolve: {
      questionary: QuestionaryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(questionaryRoute)],
  exports: [RouterModule],
})
export class QuestionaryRoutingModule {}

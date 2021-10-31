import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BargainComponent } from '../list/bargain.component';
import { BargainDetailComponent } from '../detail/bargain-detail.component';
import { BargainUpdateComponent } from '../update/bargain-update.component';
import { BargainRoutingResolveService } from './bargain-routing-resolve.service';

const bargainRoute: Routes = [
  {
    path: '',
    component: BargainComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BargainDetailComponent,
    resolve: {
      bargain: BargainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BargainUpdateComponent,
    resolve: {
      bargain: BargainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BargainUpdateComponent,
    resolve: {
      bargain: BargainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bargainRoute)],
  exports: [RouterModule],
})
export class BargainRoutingModule {}

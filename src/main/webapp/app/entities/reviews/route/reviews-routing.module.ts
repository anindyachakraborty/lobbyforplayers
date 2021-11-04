import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ReviewsComponent } from '../list/reviews.component';
import { ReviewsDetailComponent } from '../detail/reviews-detail.component';
import { ReviewsUpdateComponent } from '../update/reviews-update.component';
import { ReviewsRoutingResolveService } from './reviews-routing-resolve.service';

const reviewsRoute: Routes = [
  {
    path: '',
    component: ReviewsComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReviewsDetailComponent,
    resolve: {
      reviews: ReviewsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReviewsUpdateComponent,
    resolve: {
      reviews: ReviewsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReviewsUpdateComponent,
    resolve: {
      reviews: ReviewsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(reviewsRoute)],
  exports: [RouterModule],
})
export class ReviewsRoutingModule {}

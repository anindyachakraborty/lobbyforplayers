import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReviewsComponent } from './list/reviews.component';
import { ReviewsDetailComponent } from './detail/reviews-detail.component';
import { ReviewsUpdateComponent } from './update/reviews-update.component';
import { ReviewsDeleteDialogComponent } from './delete/reviews-delete-dialog.component';
import { ReviewsRoutingModule } from './route/reviews-routing.module';

@NgModule({
  imports: [SharedModule, ReviewsRoutingModule],
  declarations: [ReviewsComponent, ReviewsDetailComponent, ReviewsUpdateComponent, ReviewsDeleteDialogComponent],
  entryComponents: [ReviewsDeleteDialogComponent],
})
export class ReviewsModule {}

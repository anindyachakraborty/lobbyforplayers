import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IReviews } from '../reviews.model';
import { ReviewsService } from '../service/reviews.service';

@Component({
  templateUrl: './reviews-delete-dialog.component.html',
})
export class ReviewsDeleteDialogComponent {
  reviews?: IReviews;

  constructor(protected reviewsService: ReviewsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.reviewsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}

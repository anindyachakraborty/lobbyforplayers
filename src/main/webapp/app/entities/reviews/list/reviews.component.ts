import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReviews } from '../reviews.model';
import { ReviewsService } from '../service/reviews.service';
import { ReviewsDeleteDialogComponent } from '../delete/reviews-delete-dialog.component';

@Component({
  selector: 'jhi-reviews',
  templateUrl: './reviews.component.html',
})
export class ReviewsComponent implements OnInit {
  reviews?: IReviews[];
  isLoading = false;

  constructor(protected reviewsService: ReviewsService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.reviewsService.query().subscribe({
      next: (res: HttpResponse<IReviews[]>) => {
        this.isLoading = false;
        this.reviews = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IReviews): string {
    return item.id!;
  }

  delete(reviews: IReviews): void {
    const modalRef = this.modalService.open(ReviewsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.reviews = reviews;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}

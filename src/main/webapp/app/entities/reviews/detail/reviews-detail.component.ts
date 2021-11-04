import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReviews } from '../reviews.model';

@Component({
  selector: 'jhi-reviews-detail',
  templateUrl: './reviews-detail.component.html',
})
export class ReviewsDetailComponent implements OnInit {
  reviews: IReviews | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reviews }) => {
      this.reviews = reviews;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IReviews, Reviews } from '../reviews.model';
import { ReviewsService } from '../service/reviews.service';

@Component({
  selector: 'jhi-reviews-update',
  templateUrl: './reviews-update.component.html',
})
export class ReviewsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    question: [null, [Validators.required]],
    rating: [null, [Validators.required]],
    username: [null, [Validators.required]],
    orderId: [],
  });

  constructor(protected reviewsService: ReviewsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reviews }) => {
      this.updateForm(reviews);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reviews = this.createFromForm();
    if (reviews.id !== undefined) {
      this.subscribeToSaveResponse(this.reviewsService.update(reviews));
    } else {
      this.subscribeToSaveResponse(this.reviewsService.create(reviews));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReviews>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(reviews: IReviews): void {
    this.editForm.patchValue({
      id: reviews.id,
      question: reviews.question,
      rating: reviews.rating,
      username: reviews.username,
      orderId: reviews.orderId,
    });
  }

  protected createFromForm(): IReviews {
    return {
      ...new Reviews(),
      id: this.editForm.get(['id'])!.value,
      question: this.editForm.get(['question'])!.value,
      rating: this.editForm.get(['rating'])!.value,
      username: this.editForm.get(['username'])!.value,
      orderId: this.editForm.get(['orderId'])!.value,
    };
  }
}

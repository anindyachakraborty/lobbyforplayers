import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IReviews, getReviewsIdentifier } from '../reviews.model';

export type EntityResponseType = HttpResponse<IReviews>;
export type EntityArrayResponseType = HttpResponse<IReviews[]>;

@Injectable({ providedIn: 'root' })
export class ReviewsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/reviews');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(reviews: IReviews): Observable<EntityResponseType> {
    return this.http.post<IReviews>(this.resourceUrl, reviews, { observe: 'response' });
  }

  update(reviews: IReviews): Observable<EntityResponseType> {
    return this.http.put<IReviews>(`${this.resourceUrl}/${getReviewsIdentifier(reviews) as string}`, reviews, { observe: 'response' });
  }

  partialUpdate(reviews: IReviews): Observable<EntityResponseType> {
    return this.http.patch<IReviews>(`${this.resourceUrl}/${getReviewsIdentifier(reviews) as string}`, reviews, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IReviews>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReviews[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addReviewsToCollectionIfMissing(reviewsCollection: IReviews[], ...reviewsToCheck: (IReviews | null | undefined)[]): IReviews[] {
    const reviews: IReviews[] = reviewsToCheck.filter(isPresent);
    if (reviews.length > 0) {
      const reviewsCollectionIdentifiers = reviewsCollection.map(reviewsItem => getReviewsIdentifier(reviewsItem)!);
      const reviewsToAdd = reviews.filter(reviewsItem => {
        const reviewsIdentifier = getReviewsIdentifier(reviewsItem);
        if (reviewsIdentifier == null || reviewsCollectionIdentifiers.includes(reviewsIdentifier)) {
          return false;
        }
        reviewsCollectionIdentifiers.push(reviewsIdentifier);
        return true;
      });
      return [...reviewsToAdd, ...reviewsCollection];
    }
    return reviewsCollection;
  }
}

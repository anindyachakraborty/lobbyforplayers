import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDetails, getDetailsIdentifier } from '../details.model';

export type EntityResponseType = HttpResponse<IDetails>;
export type EntityArrayResponseType = HttpResponse<IDetails[]>;

@Injectable({ providedIn: 'root' })
export class DetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(details: IDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(details);
    return this.http
      .post<IDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(details: IDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(details);
    return this.http
      .put<IDetails>(`${this.resourceUrl}/${getDetailsIdentifier(details) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(details: IDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(details);
    return this.http
      .patch<IDetails>(`${this.resourceUrl}/${getDetailsIdentifier(details) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDetailsToCollectionIfMissing(detailsCollection: IDetails[], ...detailsToCheck: (IDetails | null | undefined)[]): IDetails[] {
    const details: IDetails[] = detailsToCheck.filter(isPresent);
    if (details.length > 0) {
      const detailsCollectionIdentifiers = detailsCollection.map(detailsItem => getDetailsIdentifier(detailsItem)!);
      const detailsToAdd = details.filter(detailsItem => {
        const detailsIdentifier = getDetailsIdentifier(detailsItem);
        if (detailsIdentifier == null || detailsCollectionIdentifiers.includes(detailsIdentifier)) {
          return false;
        }
        detailsCollectionIdentifiers.push(detailsIdentifier);
        return true;
      });
      return [...detailsToAdd, ...detailsCollection];
    }
    return detailsCollection;
  }

  protected convertDateFromClient(details: IDetails): IDetails {
    return Object.assign({}, details, {
      enteredDate: details.enteredDate?.isValid() ? details.enteredDate.toJSON() : undefined,
      orderDate: details.orderDate?.isValid() ? details.orderDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.enteredDate = res.body.enteredDate ? dayjs(res.body.enteredDate) : undefined;
      res.body.orderDate = res.body.orderDate ? dayjs(res.body.orderDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((details: IDetails) => {
        details.enteredDate = details.enteredDate ? dayjs(details.enteredDate) : undefined;
        details.orderDate = details.orderDate ? dayjs(details.orderDate) : undefined;
      });
    }
    return res;
  }
}

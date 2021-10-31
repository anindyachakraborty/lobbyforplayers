import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBargain, getBargainIdentifier } from '../bargain.model';

export type EntityResponseType = HttpResponse<IBargain>;
export type EntityArrayResponseType = HttpResponse<IBargain[]>;

@Injectable({ providedIn: 'root' })
export class BargainService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bargains');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bargain: IBargain): Observable<EntityResponseType> {
    return this.http.post<IBargain>(this.resourceUrl, bargain, { observe: 'response' });
  }

  update(bargain: IBargain): Observable<EntityResponseType> {
    return this.http.put<IBargain>(`${this.resourceUrl}/${getBargainIdentifier(bargain) as number}`, bargain, { observe: 'response' });
  }

  partialUpdate(bargain: IBargain): Observable<EntityResponseType> {
    return this.http.patch<IBargain>(`${this.resourceUrl}/${getBargainIdentifier(bargain) as number}`, bargain, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBargain>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBargain[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBargainToCollectionIfMissing(bargainCollection: IBargain[], ...bargainsToCheck: (IBargain | null | undefined)[]): IBargain[] {
    const bargains: IBargain[] = bargainsToCheck.filter(isPresent);
    if (bargains.length > 0) {
      const bargainCollectionIdentifiers = bargainCollection.map(bargainItem => getBargainIdentifier(bargainItem)!);
      const bargainsToAdd = bargains.filter(bargainItem => {
        const bargainIdentifier = getBargainIdentifier(bargainItem);
        if (bargainIdentifier == null || bargainCollectionIdentifiers.includes(bargainIdentifier)) {
          return false;
        }
        bargainCollectionIdentifiers.push(bargainIdentifier);
        return true;
      });
      return [...bargainsToAdd, ...bargainCollection];
    }
    return bargainCollection;
  }
}

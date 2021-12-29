import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IItem, getItemIdentifier } from '../item.model';

export type EntityResponseType = HttpResponse<IItem>;
export type EntityArrayResponseType = HttpResponse<IItem[]>;

@Injectable({ providedIn: 'root' })
export class ItemService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/items');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(item: IItem): Observable<EntityResponseType> {
    return this.http.post<IItem>(this.resourceUrl, item, { observe: 'response' });
  }

  update(item: IItem): Observable<EntityResponseType> {
    return this.http.put<IItem>(`${this.resourceUrl}/${getItemIdentifier(item) as string}`, item, { observe: 'response' });
  }

  partialUpdate(item: IItem): Observable<EntityResponseType> {
    return this.http.patch<IItem>(`${this.resourceUrl}/${getItemIdentifier(item) as string}`, item, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  getGames(): Observable<HttpResponse<string[]>> {
    return this.http.get<string[]>(`${this.resourceUrl}/list/games`, { observe: 'response' });
  }

  minPrice(games: string[]): Observable<HttpResponse<number>> {
    return this.http.request<number>('GET', `${this.resourceUrl}/minimum/price`, {
      body: JSON.stringify(games),
      headers: { 'Content-Type': 'application/json' },
      observe: 'response',
    });
  }

  maxPrice(games: string[]): Observable<HttpResponse<number>> {
    return this.http.request<number>('GET', `${this.resourceUrl}/maximum/price`, { body: JSON.stringify(games), observe: 'response' });
  }

  filtered(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IItem[]>(`${this.resourceUrl}/filtered`, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addItemToCollectionIfMissing(itemCollection: IItem[], ...itemsToCheck: (IItem | null | undefined)[]): IItem[] {
    const items: IItem[] = itemsToCheck.filter(isPresent);
    if (items.length > 0) {
      const itemCollectionIdentifiers = itemCollection.map(itemItem => getItemIdentifier(itemItem)!);
      const itemsToAdd = items.filter(itemItem => {
        const itemIdentifier = getItemIdentifier(itemItem);
        if (itemIdentifier == null || itemCollectionIdentifiers.includes(itemIdentifier)) {
          return false;
        }
        itemCollectionIdentifiers.push(itemIdentifier);
        return true;
      });
      return [...itemsToAdd, ...itemCollection];
    }
    return itemCollection;
  }
}

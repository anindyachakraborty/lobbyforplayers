import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IChats, getChatsIdentifier } from '../chats.model';

export type EntityResponseType = HttpResponse<IChats>;
export type EntityArrayResponseType = HttpResponse<IChats[]>;

@Injectable({ providedIn: 'root' })
export class ChatsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/chats');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(chats: IChats): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chats);
    return this.http
      .post<IChats>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(chats: IChats): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chats);
    return this.http
      .put<IChats>(`${this.resourceUrl}/${getChatsIdentifier(chats) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(chats: IChats): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chats);
    return this.http
      .patch<IChats>(`${this.resourceUrl}/${getChatsIdentifier(chats) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IChats>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IChats[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addChatsToCollectionIfMissing(chatsCollection: IChats[], ...chatsToCheck: (IChats | null | undefined)[]): IChats[] {
    const chats: IChats[] = chatsToCheck.filter(isPresent);
    if (chats.length > 0) {
      const chatsCollectionIdentifiers = chatsCollection.map(chatsItem => getChatsIdentifier(chatsItem)!);
      const chatsToAdd = chats.filter(chatsItem => {
        const chatsIdentifier = getChatsIdentifier(chatsItem);
        if (chatsIdentifier == null || chatsCollectionIdentifiers.includes(chatsIdentifier)) {
          return false;
        }
        chatsCollectionIdentifiers.push(chatsIdentifier);
        return true;
      });
      return [...chatsToAdd, ...chatsCollection];
    }
    return chatsCollection;
  }

  protected convertDateFromClient(chats: IChats): IChats {
    return Object.assign({}, chats, {
      timeStamp: chats.timeStamp?.isValid() ? chats.timeStamp.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.timeStamp = res.body.timeStamp ? dayjs(res.body.timeStamp) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((chats: IChats) => {
        chats.timeStamp = chats.timeStamp ? dayjs(chats.timeStamp) : undefined;
      });
    }
    return res;
  }
}

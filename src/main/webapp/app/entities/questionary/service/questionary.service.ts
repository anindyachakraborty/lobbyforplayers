import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IQuestionary, getQuestionaryIdentifier } from '../questionary.model';

export type EntityResponseType = HttpResponse<IQuestionary>;
export type EntityArrayResponseType = HttpResponse<IQuestionary[]>;

@Injectable({ providedIn: 'root' })
export class QuestionaryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/questionaries');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(questionary: IQuestionary): Observable<EntityResponseType> {
    return this.http.post<IQuestionary>(this.resourceUrl, questionary, { observe: 'response' });
  }

  update(questionary: IQuestionary): Observable<EntityResponseType> {
    return this.http.put<IQuestionary>(`${this.resourceUrl}/${getQuestionaryIdentifier(questionary) as string}`, questionary, {
      observe: 'response',
    });
  }

  partialUpdate(questionary: IQuestionary): Observable<EntityResponseType> {
    return this.http.patch<IQuestionary>(`${this.resourceUrl}/${getQuestionaryIdentifier(questionary) as string}`, questionary, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IQuestionary>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQuestionary[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addQuestionaryToCollectionIfMissing(
    questionaryCollection: IQuestionary[],
    ...questionariesToCheck: (IQuestionary | null | undefined)[]
  ): IQuestionary[] {
    const questionaries: IQuestionary[] = questionariesToCheck.filter(isPresent);
    if (questionaries.length > 0) {
      const questionaryCollectionIdentifiers = questionaryCollection.map(questionaryItem => getQuestionaryIdentifier(questionaryItem)!);
      const questionariesToAdd = questionaries.filter(questionaryItem => {
        const questionaryIdentifier = getQuestionaryIdentifier(questionaryItem);
        if (questionaryIdentifier == null || questionaryCollectionIdentifiers.includes(questionaryIdentifier)) {
          return false;
        }
        questionaryCollectionIdentifiers.push(questionaryIdentifier);
        return true;
      });
      return [...questionariesToAdd, ...questionaryCollection];
    }
    return questionaryCollection;
  }
}

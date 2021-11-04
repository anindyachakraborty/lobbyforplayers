import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPayments, getPaymentsIdentifier } from '../payments.model';

export type EntityResponseType = HttpResponse<IPayments>;
export type EntityArrayResponseType = HttpResponse<IPayments[]>;

@Injectable({ providedIn: 'root' })
export class PaymentsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payments');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(payments: IPayments): Observable<EntityResponseType> {
    return this.http.post<IPayments>(this.resourceUrl, payments, { observe: 'response' });
  }

  update(payments: IPayments): Observable<EntityResponseType> {
    return this.http.put<IPayments>(`${this.resourceUrl}/${getPaymentsIdentifier(payments) as string}`, payments, { observe: 'response' });
  }

  partialUpdate(payments: IPayments): Observable<EntityResponseType> {
    return this.http.patch<IPayments>(`${this.resourceUrl}/${getPaymentsIdentifier(payments) as string}`, payments, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IPayments>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPayments[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPaymentsToCollectionIfMissing(paymentsCollection: IPayments[], ...paymentsToCheck: (IPayments | null | undefined)[]): IPayments[] {
    const payments: IPayments[] = paymentsToCheck.filter(isPresent);
    if (payments.length > 0) {
      const paymentsCollectionIdentifiers = paymentsCollection.map(paymentsItem => getPaymentsIdentifier(paymentsItem)!);
      const paymentsToAdd = payments.filter(paymentsItem => {
        const paymentsIdentifier = getPaymentsIdentifier(paymentsItem);
        if (paymentsIdentifier == null || paymentsCollectionIdentifiers.includes(paymentsIdentifier)) {
          return false;
        }
        paymentsCollectionIdentifiers.push(paymentsIdentifier);
        return true;
      });
      return [...paymentsToAdd, ...paymentsCollection];
    }
    return paymentsCollection;
  }
}

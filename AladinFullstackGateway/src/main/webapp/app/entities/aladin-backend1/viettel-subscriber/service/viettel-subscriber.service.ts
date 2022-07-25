import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IViettelSubscriber, getViettelSubscriberIdentifier } from '../viettel-subscriber.model';

export type EntityResponseType = HttpResponse<IViettelSubscriber>;
export type EntityArrayResponseType = HttpResponse<IViettelSubscriber[]>;

@Injectable({ providedIn: 'root' })
export class ViettelSubscriberService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/viettel-subscribers','aladinbackend1');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/viettel-subscribers','aladinbackend1');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(viettelSubscriber: IViettelSubscriber): Observable<EntityResponseType> {
    return this.http.post<IViettelSubscriber>(this.resourceUrl, viettelSubscriber, { observe: 'response' });
  }

  update(viettelSubscriber: IViettelSubscriber): Observable<EntityResponseType> {
    return this.http.put<IViettelSubscriber>(
      `${this.resourceUrl}/${getViettelSubscriberIdentifier(viettelSubscriber) as number}`,
      viettelSubscriber,
      { observe: 'response' }
    );
  }

  partialUpdate(viettelSubscriber: IViettelSubscriber): Observable<EntityResponseType> {
    return this.http.patch<IViettelSubscriber>(
      `${this.resourceUrl}/${getViettelSubscriberIdentifier(viettelSubscriber) as number}`,
      viettelSubscriber,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IViettelSubscriber>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IViettelSubscriber[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IViettelSubscriber[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addViettelSubscriberToCollectionIfMissing(
    viettelSubscriberCollection: IViettelSubscriber[],
    ...viettelSubscribersToCheck: (IViettelSubscriber | null | undefined)[]
  ): IViettelSubscriber[] {
    const viettelSubscribers: IViettelSubscriber[] = viettelSubscribersToCheck.filter(isPresent);
    if (viettelSubscribers.length > 0) {
      const viettelSubscriberCollectionIdentifiers = viettelSubscriberCollection.map(
        viettelSubscriberItem => getViettelSubscriberIdentifier(viettelSubscriberItem)!
      );
      const viettelSubscribersToAdd = viettelSubscribers.filter(viettelSubscriberItem => {
        const viettelSubscriberIdentifier = getViettelSubscriberIdentifier(viettelSubscriberItem);
        if (viettelSubscriberIdentifier == null || viettelSubscriberCollectionIdentifiers.includes(viettelSubscriberIdentifier)) {
          return false;
        }
        viettelSubscriberCollectionIdentifiers.push(viettelSubscriberIdentifier);
        return true;
      });
      return [...viettelSubscribersToAdd, ...viettelSubscriberCollection];
    }
    return viettelSubscriberCollection;
  }
}

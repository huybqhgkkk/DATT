import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IViettelSubscriber2, getViettelSubscriber2Identifier } from '../viettel-subscriber-2.model';

export type EntityResponseType = HttpResponse<IViettelSubscriber2>;
export type EntityArrayResponseType = HttpResponse<IViettelSubscriber2[]>;

@Injectable({ providedIn: 'root' })
export class ViettelSubscriber2Service {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/viettel-subscriber-2-s','aladinbackend2');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/viettel-subscriber-2-s','aladinbackend2');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(viettelSubscriber2: IViettelSubscriber2): Observable<EntityResponseType> {
    return this.http.post<IViettelSubscriber2>(this.resourceUrl, viettelSubscriber2, { observe: 'response' });
  }

  update(viettelSubscriber2: IViettelSubscriber2): Observable<EntityResponseType> {
    return this.http.put<IViettelSubscriber2>(
      `${this.resourceUrl}/${getViettelSubscriber2Identifier(viettelSubscriber2) as number}`,
      viettelSubscriber2,
      { observe: 'response' }
    );
  }

  partialUpdate(viettelSubscriber2: IViettelSubscriber2): Observable<EntityResponseType> {
    return this.http.patch<IViettelSubscriber2>(
      `${this.resourceUrl}/${getViettelSubscriber2Identifier(viettelSubscriber2) as number}`,
      viettelSubscriber2,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IViettelSubscriber2>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IViettelSubscriber2[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IViettelSubscriber2[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addViettelSubscriber2ToCollectionIfMissing(
    viettelSubscriber2Collection: IViettelSubscriber2[],
    ...viettelSubscriber2sToCheck: (IViettelSubscriber2 | null | undefined)[]
  ): IViettelSubscriber2[] {
    const viettelSubscriber2s: IViettelSubscriber2[] = viettelSubscriber2sToCheck.filter(isPresent);
    if (viettelSubscriber2s.length > 0) {
      const viettelSubscriber2CollectionIdentifiers = viettelSubscriber2Collection.map(
        viettelSubscriber2Item => getViettelSubscriber2Identifier(viettelSubscriber2Item)!
      );
      const viettelSubscriber2sToAdd = viettelSubscriber2s.filter(viettelSubscriber2Item => {
        const viettelSubscriber2Identifier = getViettelSubscriber2Identifier(viettelSubscriber2Item);
        if (viettelSubscriber2Identifier == null || viettelSubscriber2CollectionIdentifiers.includes(viettelSubscriber2Identifier)) {
          return false;
        }
        viettelSubscriber2CollectionIdentifiers.push(viettelSubscriber2Identifier);
        return true;
      });
      return [...viettelSubscriber2sToAdd, ...viettelSubscriber2Collection];
    }
    return viettelSubscriber2Collection;
  }
}

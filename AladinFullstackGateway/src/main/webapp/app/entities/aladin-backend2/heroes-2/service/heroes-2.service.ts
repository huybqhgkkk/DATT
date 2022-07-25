import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IHeroes2, getHeroes2Identifier } from '../heroes-2.model';

export type EntityResponseType = HttpResponse<IHeroes2>;
export type EntityArrayResponseType = HttpResponse<IHeroes2[]>;

@Injectable({ providedIn: 'root' })
export class Heroes2Service {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/heroes-2-s','aladinbackend2');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/heroes-2-s','aladinbackend2');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(heroes2: IHeroes2): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(heroes2);
    return this.http
      .post<IHeroes2>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(heroes2: IHeroes2): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(heroes2);
    return this.http
      .put<IHeroes2>(`${this.resourceUrl}/${getHeroes2Identifier(heroes2) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(heroes2: IHeroes2): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(heroes2);
    return this.http
      .patch<IHeroes2>(`${this.resourceUrl}/${getHeroes2Identifier(heroes2) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHeroes2>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHeroes2[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHeroes2[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addHeroes2ToCollectionIfMissing(heroes2Collection: IHeroes2[], ...heroes2sToCheck: (IHeroes2 | null | undefined)[]): IHeroes2[] {
    const heroes2s: IHeroes2[] = heroes2sToCheck.filter(isPresent);
    if (heroes2s.length > 0) {
      const heroes2CollectionIdentifiers = heroes2Collection.map(heroes2Item => getHeroes2Identifier(heroes2Item)!);
      const heroes2sToAdd = heroes2s.filter(heroes2Item => {
        const heroes2Identifier = getHeroes2Identifier(heroes2Item);
        if (heroes2Identifier == null || heroes2CollectionIdentifiers.includes(heroes2Identifier)) {
          return false;
        }
        heroes2CollectionIdentifiers.push(heroes2Identifier);
        return true;
      });
      return [...heroes2sToAdd, ...heroes2Collection];
    }
    return heroes2Collection;
  }

  protected convertDateFromClient(heroes2: IHeroes2): IHeroes2 {
    return Object.assign({}, heroes2, {
      create_time: heroes2.create_time?.isValid() ? heroes2.create_time.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.create_time = res.body.create_time ? dayjs(res.body.create_time) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((heroes2: IHeroes2) => {
        heroes2.create_time = heroes2.create_time ? dayjs(heroes2.create_time) : undefined;
      });
    }
    return res;
  }
}

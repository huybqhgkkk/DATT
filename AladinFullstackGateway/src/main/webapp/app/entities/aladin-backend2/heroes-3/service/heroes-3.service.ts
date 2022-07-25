import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IHeroes3, getHeroes3Identifier } from '../heroes-3.model';

export type EntityResponseType = HttpResponse<IHeroes3>;
export type EntityArrayResponseType = HttpResponse<IHeroes3[]>;

@Injectable({ providedIn: 'root' })
export class Heroes3Service {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/heroes-3-s','aladinbackend2');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/heroes-3-s','aladinbackend2');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(heroes3: IHeroes3): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(heroes3);
    return this.http
      .post<IHeroes3>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(heroes3: IHeroes3): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(heroes3);
    return this.http
      .put<IHeroes3>(`${this.resourceUrl}/${getHeroes3Identifier(heroes3) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(heroes3: IHeroes3): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(heroes3);
    return this.http
      .patch<IHeroes3>(`${this.resourceUrl}/${getHeroes3Identifier(heroes3) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHeroes3>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHeroes3[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHeroes3[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addHeroes3ToCollectionIfMissing(heroes3Collection: IHeroes3[], ...heroes3sToCheck: (IHeroes3 | null | undefined)[]): IHeroes3[] {
    const heroes3s: IHeroes3[] = heroes3sToCheck.filter(isPresent);
    if (heroes3s.length > 0) {
      const heroes3CollectionIdentifiers = heroes3Collection.map(heroes3Item => getHeroes3Identifier(heroes3Item)!);
      const heroes3sToAdd = heroes3s.filter(heroes3Item => {
        const heroes3Identifier = getHeroes3Identifier(heroes3Item);
        if (heroes3Identifier == null || heroes3CollectionIdentifiers.includes(heroes3Identifier)) {
          return false;
        }
        heroes3CollectionIdentifiers.push(heroes3Identifier);
        return true;
      });
      return [...heroes3sToAdd, ...heroes3Collection];
    }
    return heroes3Collection;
  }

  protected convertDateFromClient(heroes3: IHeroes3): IHeroes3 {
    return Object.assign({}, heroes3, {
      create_time: heroes3.create_time?.isValid() ? heroes3.create_time.toJSON() : undefined,
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
      res.body.forEach((heroes3: IHeroes3) => {
        heroes3.create_time = heroes3.create_time ? dayjs(heroes3.create_time) : undefined;
      });
    }
    return res;
  }
}

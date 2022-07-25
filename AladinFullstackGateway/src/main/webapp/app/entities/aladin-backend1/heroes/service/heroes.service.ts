import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IHeroes, getHeroesIdentifier } from '../heroes.model';

export type EntityResponseType = HttpResponse<IHeroes>;
export type EntityArrayResponseType = HttpResponse<IHeroes[]>;

@Injectable({ providedIn: 'root' })
export class HeroesService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/heroes','aladinbackend1');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/heroes','aladinbackend1');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(heroes: IHeroes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(heroes);
    return this.http
      .post<IHeroes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(heroes: IHeroes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(heroes);
    return this.http
      .put<IHeroes>(`${this.resourceUrl}/${getHeroesIdentifier(heroes) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(heroes: IHeroes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(heroes);
    return this.http
      .patch<IHeroes>(`${this.resourceUrl}/${getHeroesIdentifier(heroes) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHeroes>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHeroes[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHeroes[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addHeroesToCollectionIfMissing(heroesCollection: IHeroes[], ...heroesToCheck: (IHeroes | null | undefined)[]): IHeroes[] {
    const heroes: IHeroes[] = heroesToCheck.filter(isPresent);
    if (heroes.length > 0) {
      const heroesCollectionIdentifiers = heroesCollection.map(heroesItem => getHeroesIdentifier(heroesItem)!);
      const heroesToAdd = heroes.filter(heroesItem => {
        const heroesIdentifier = getHeroesIdentifier(heroesItem);
        if (heroesIdentifier == null || heroesCollectionIdentifiers.includes(heroesIdentifier)) {
          return false;
        }
        heroesCollectionIdentifiers.push(heroesIdentifier);
        return true;
      });
      return [...heroesToAdd, ...heroesCollection];
    }
    return heroesCollection;
  }

  protected convertDateFromClient(heroes: IHeroes): IHeroes {
    return Object.assign({}, heroes, {
      create_time: heroes.create_time?.isValid() ? heroes.create_time.toJSON() : undefined,
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
      res.body.forEach((heroes: IHeroes) => {
        heroes.create_time = heroes.create_time ? dayjs(heroes.create_time) : undefined;
      });
    }
    return res;
  }
}

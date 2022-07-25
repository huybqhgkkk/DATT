import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INews, getNewsIdentifier } from '../news.model';

export type EntityResponseType = HttpResponse<INews>;
export type EntityArrayResponseType = HttpResponse<INews[]>;

@Injectable({ providedIn: 'root' })
export class NewsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/news', 'aladintechcobackendtest');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(news: INews): Observable<EntityResponseType> {
    return this.http.post<INews>(this.resourceUrl, news, { observe: 'response' });
  }

  update(news: INews): Observable<EntityResponseType> {
    return this.http.put<INews>(`${this.resourceUrl}/${getNewsIdentifier(news) as number}`, news, { observe: 'response' });
  }

  partialUpdate(news: INews): Observable<EntityResponseType> {
    return this.http.patch<INews>(`${this.resourceUrl}/${getNewsIdentifier(news) as number}`, news, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INews>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INews[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNewsToCollectionIfMissing(newsCollection: INews[], ...newsToCheck: (INews | null | undefined)[]): INews[] {
    const news: INews[] = newsToCheck.filter(isPresent);
    if (news.length > 0) {
      const newsCollectionIdentifiers = newsCollection.map(newsItem => getNewsIdentifier(newsItem)!);
      const newsToAdd = news.filter(newsItem => {
        const newsIdentifier = getNewsIdentifier(newsItem);
        if (newsIdentifier == null || newsCollectionIdentifiers.includes(newsIdentifier)) {
          return false;
        }
        newsCollectionIdentifiers.push(newsIdentifier);
        return true;
      });
      return [...newsToAdd, ...newsCollection];
    }
    return newsCollection;
  }
}

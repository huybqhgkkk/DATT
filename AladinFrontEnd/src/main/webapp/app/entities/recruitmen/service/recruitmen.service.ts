import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRecruitmen, getRecruitmenIdentifier } from '../recruitmen.model';

export type EntityResponseType = HttpResponse<IRecruitmen>;
export type EntityArrayResponseType = HttpResponse<IRecruitmen[]>;

@Injectable({ providedIn: 'root' })
export class RecruitmenService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/recruitments', 'aladintechcobackendtest');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(recruitmen: IRecruitmen): Observable<EntityResponseType> {
    return this.http.post<IRecruitmen>(this.resourceUrl, recruitmen, { observe: 'response' });
  }

  update(recruitmen: IRecruitmen): Observable<EntityResponseType> {
    return this.http.put<IRecruitmen>(`${this.resourceUrl}/${getRecruitmenIdentifier(recruitmen) as number}`, recruitmen, {
      observe: 'response',
    });
  }

  partialUpdate(recruitmen: IRecruitmen): Observable<EntityResponseType> {
    return this.http.patch<IRecruitmen>(`${this.resourceUrl}/${getRecruitmenIdentifier(recruitmen) as number}`, recruitmen, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRecruitmen>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRecruitmen[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRecruitmenToCollectionIfMissing(
    recruitmenCollection: IRecruitmen[],
    ...recruitmenToCheck: (IRecruitmen | null | undefined)[]
  ): IRecruitmen[] {
    const recruitmen: IRecruitmen[] = recruitmenToCheck.filter(isPresent);
    if (recruitmen.length > 0) {
      const recruitmenCollectionIdentifiers = recruitmenCollection.map(recruitmenItem => getRecruitmenIdentifier(recruitmenItem)!);
      const recruitmenToAdd = recruitmen.filter(recruitmenItem => {
        const recruitmenIdentifier = getRecruitmenIdentifier(recruitmenItem);
        if (recruitmenIdentifier == null || recruitmenCollectionIdentifiers.includes(recruitmenIdentifier)) {
          return false;
        }
        recruitmenCollectionIdentifiers.push(recruitmenIdentifier);
        return true;
      });
      return [...recruitmenToAdd, ...recruitmenCollection];
    }
    return recruitmenCollection;
  }
}

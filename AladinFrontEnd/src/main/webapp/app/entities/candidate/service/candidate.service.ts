import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICandidate, getCandidateIdentifier } from '../candidate.model';

export type EntityResponseType = HttpResponse<ICandidate>;
export type EntityArrayResponseType = HttpResponse<ICandidate[]>;

@Injectable({ providedIn: 'root' })
export class CandidateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/candidates', 'aladintechcobackendtest');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(candidate: ICandidate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(candidate);
    return this.http
      .post<ICandidate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(candidate: ICandidate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(candidate);
    return this.http
      .put<ICandidate>(`${this.resourceUrl}/${getCandidateIdentifier(candidate) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(candidate: ICandidate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(candidate);
    return this.http
      .patch<ICandidate>(`${this.resourceUrl}/${getCandidateIdentifier(candidate) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICandidate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICandidate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCandidateToCollectionIfMissing(
    candidateCollection: ICandidate[],
    ...candidatesToCheck: (ICandidate | null | undefined)[]
  ): ICandidate[] {
    const candidates: ICandidate[] = candidatesToCheck.filter(isPresent);
    if (candidates.length > 0) {
      const candidateCollectionIdentifiers = candidateCollection.map(candidateItem => getCandidateIdentifier(candidateItem)!);
      const candidatesToAdd = candidates.filter(candidateItem => {
        const candidateIdentifier = getCandidateIdentifier(candidateItem);
        if (candidateIdentifier == null || candidateCollectionIdentifiers.includes(candidateIdentifier)) {
          return false;
        }
        candidateCollectionIdentifiers.push(candidateIdentifier);
        return true;
      });
      return [...candidatesToAdd, ...candidateCollection];
    }
    return candidateCollection;
  }

  protected convertDateFromClient(candidate: ICandidate): ICandidate {
    return Object.assign({}, candidate, {
      birthday: candidate.birthday?.isValid() ? candidate.birthday.toJSON() : undefined,
      timeregister: candidate.dateRegister?.isValid() ? candidate.dateRegister.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.birthday = res.body.birthday ? dayjs(res.body.birthday) : undefined;
      res.body.dateRegister = res.body.dateRegister ? dayjs(res.body.dateRegister) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((candidate: ICandidate) => {
        candidate.birthday = candidate.birthday ? dayjs(candidate.birthday) : undefined;
        candidate.dateRegister = candidate.dateRegister ? dayjs(candidate.dateRegister) : undefined;
      });
    }
    return res;
  }
}

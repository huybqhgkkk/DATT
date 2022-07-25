import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmailtemplate, getEmailtemplateIdentifier } from '../emailtemplate.model';

export type EntityResponseType = HttpResponse<IEmailtemplate>;
export type EntityArrayResponseType = HttpResponse<IEmailtemplate[]>;

@Injectable({ providedIn: 'root' })
export class EmailtemplateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/emailtemplates', 'aladintechcobackendtest');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(emailtemplate: IEmailtemplate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emailtemplate);
    return this.http
      .post<IEmailtemplate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(emailtemplate: IEmailtemplate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emailtemplate);
    return this.http
      .put<IEmailtemplate>(`${this.resourceUrl}/${getEmailtemplateIdentifier(emailtemplate) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(emailtemplate: IEmailtemplate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emailtemplate);
    return this.http
      .patch<IEmailtemplate>(`${this.resourceUrl}/${getEmailtemplateIdentifier(emailtemplate) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmailtemplate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmailtemplate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEmailtemplateToCollectionIfMissing(
    emailtemplateCollection: IEmailtemplate[],
    ...emailtemplatesToCheck: (IEmailtemplate | null | undefined)[]
  ): IEmailtemplate[] {
    const emailtemplates: IEmailtemplate[] = emailtemplatesToCheck.filter(isPresent);
    if (emailtemplates.length > 0) {
      const emailtemplateCollectionIdentifiers = emailtemplateCollection.map(
        emailtemplateItem => getEmailtemplateIdentifier(emailtemplateItem)!
      );
      const emailtemplatesToAdd = emailtemplates.filter(emailtemplateItem => {
        const emailtemplateIdentifier = getEmailtemplateIdentifier(emailtemplateItem);
        if (emailtemplateIdentifier == null || emailtemplateCollectionIdentifiers.includes(emailtemplateIdentifier)) {
          return false;
        }
        emailtemplateCollectionIdentifiers.push(emailtemplateIdentifier);
        return true;
      });
      return [...emailtemplatesToAdd, ...emailtemplateCollection];
    }
    return emailtemplateCollection;
  }

  protected convertDateFromClient(emailtemplate: IEmailtemplate): IEmailtemplate {
    return Object.assign({}, emailtemplate, {
      datetime: emailtemplate.datetime?.isValid() ? emailtemplate.datetime.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.datetime = res.body.datetime ? dayjs(res.body.datetime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((emailtemplate: IEmailtemplate) => {
        emailtemplate.datetime = emailtemplate.datetime ? dayjs(emailtemplate.datetime) : undefined;
      });
    }
    return res;
  }
}

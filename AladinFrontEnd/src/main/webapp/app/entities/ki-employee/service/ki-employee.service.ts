import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKiEmployee, getKiEmployeeIdentifier } from '../ki-employee.model';

export type EntityResponseType = HttpResponse<IKiEmployee>;
export type EntityArrayResponseType = HttpResponse<IKiEmployee[]>;

@Injectable({ providedIn: 'root' })
export class KiEmployeeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ki-employees', 'aladintechcobackendtest');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(kiEmployee: IKiEmployee): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kiEmployee);
    return this.http
      .post<IKiEmployee>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(kiEmployee: IKiEmployee): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kiEmployee);
    return this.http
      .put<IKiEmployee>(`${this.resourceUrl}/${getKiEmployeeIdentifier(kiEmployee) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(kiEmployee: IKiEmployee): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kiEmployee);
    return this.http
      .patch<IKiEmployee>(`${this.resourceUrl}/${getKiEmployeeIdentifier(kiEmployee) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IKiEmployee>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IKiEmployee[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addKiEmployeeToCollectionIfMissing(
    kiEmployeeCollection: IKiEmployee[],
    ...kiEmployeesToCheck: (IKiEmployee | null | undefined)[]
  ): IKiEmployee[] {
    const kiEmployees: IKiEmployee[] = kiEmployeesToCheck.filter(isPresent);
    if (kiEmployees.length > 0) {
      const kiEmployeeCollectionIdentifiers = kiEmployeeCollection.map(kiEmployeeItem => getKiEmployeeIdentifier(kiEmployeeItem)!);
      const kiEmployeesToAdd = kiEmployees.filter(kiEmployeeItem => {
        const kiEmployeeIdentifier = getKiEmployeeIdentifier(kiEmployeeItem);
        if (kiEmployeeIdentifier == null || kiEmployeeCollectionIdentifiers.includes(kiEmployeeIdentifier)) {
          return false;
        }
        kiEmployeeCollectionIdentifiers.push(kiEmployeeIdentifier);
        return true;
      });
      return [...kiEmployeesToAdd, ...kiEmployeeCollection];
    }
    return kiEmployeeCollection;
  }

  protected convertDateFromClient(kiEmployee: IKiEmployee): IKiEmployee {
    return Object.assign({}, kiEmployee, {
      date_time: kiEmployee.date_time?.isValid() ? kiEmployee.date_time.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date_time = res.body.date_time ? dayjs(res.body.date_time) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((kiEmployee: IKiEmployee) => {
        kiEmployee.date_time = kiEmployee.date_time ? dayjs(kiEmployee.date_time) : undefined;
      });
    }
    return res;
  }
}

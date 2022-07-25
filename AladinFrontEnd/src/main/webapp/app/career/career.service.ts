import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProduct } from '../product/product.model';
import { ICareer } from './career.model';
import { IRecruitmen } from '../entities/recruitmen/recruitmen.model';
import { Candidate } from 'app/entities/candidate/candidate.model';

export type EntityResponseCareer = HttpResponse<IRecruitmen>;
export type EntityArrayResponseCareer = HttpResponse<IRecruitmen[]>;

@Injectable({ providedIn: 'root' })
export class CareerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/recruitments', 'aladintechcobackendtest');
  protected resourceCandidateUrl = this.applicationConfigService.getEndpointFor('api/candidates', 'aladintechcobackendtest');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  // create(typeService: IService): Observable<EntityResponseService> {
  //   return this.http.post<IService>(this.resourceUrl, typeService, { observe: 'response' });
  // }
  //
  // update(typeService: IService): Observable<EntityResponseService> {
  //   return this.http.put<IService>(`${this.resourceUrl}/${getServiceIdentifier(typeService) as number}`, typeService, { observe: 'response' });
  // }
  //
  // partialUpdate(typeService: IService): Observable<EntityResponseService> {
  //   return this.http.patch<IService>(`${this.resourceUrl}/${getServiceIdentifier(typeService) as number}`, typeService, {
  //     observe: 'response',
  //   });
  // }

  find(id: number): Observable<EntityResponseCareer> {
    return this.http.get<IRecruitmen>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseCareer> {
    const options = createRequestOption(req);
    return this.http.get<IRecruitmen[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  recruitment(body?: IRecruitmen): Observable<unknown> {
    return this.http.post<Candidate>(`${this.resourceCandidateUrl}`, { ...body, observe: 'response' });
  }

  // findProductBySericeType(id: number, req?: any): Observable<HttpResponse<IProduct[]>> {
  //   const options = createRequestOption(req);
  //   return this.http.get<IProduct[]>(`${this.resourceUrl}/${id}/products`, { params: options, observe: 'response' });
  // }

  //   delete(id: number): Observable<HttpResponse<{}>> {
  //     return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  //   }
  //
  //   addServiceToCollectionIfMissing(typeServiceCollection: IService[], ...typeServicesToCheck: (IService | null | undefined)[]): IService[] {
  //     const typeServices: IService[] = typeServicesToCheck.filter(isPresent);
  //     if (typeServices.length > 0) {
  //       const typeServiceCollectionIdentifiers = typeServiceCollection.map(typeServiceItem => getServiceIdentifier(typeServiceItem)!);
  //       const typeServicesToAdd = typeServices.filter(typeServiceItem => {
  //         const typeServiceIdentifier = getServiceIdentifier(typeServiceItem);
  //         if (typeServiceIdentifier == null || typeServiceCollectionIdentifiers.includes(typeServiceIdentifier)) {
  //           return false;
  //         }
  //         typeServiceCollectionIdentifiers.push(typeServiceIdentifier);
  //         return true;
  //       });
  //       return [...typeServicesToAdd, ...typeServiceCollection];
  //     }
  //     return typeServiceCollection;
  //   }
}

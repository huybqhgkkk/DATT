import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IService, getServiceIdentifier } from './service.model';
import { IProduct } from '../entities/product/product.model';

export type EntityResponseService = HttpResponse<IService>;
export type EntityArrayResponseService = HttpResponse<IService[]>;

@Injectable({ providedIn: 'root' })
export class ServiceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/services', 'aladintechcobackendtest');

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

  find(id: number): Observable<EntityResponseService> {
    return this.http.get<IService>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseService> {
    const options = createRequestOption(req);
    return this.http.get<IService[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  findProductByServicesId(id: number): Observable<HttpResponse<IProduct[]>> {
    return this.http.get<IProduct[]>(this.applicationConfigService.getEndpointFor(`api/products/services/${id}`), { observe: 'response' });
  }

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

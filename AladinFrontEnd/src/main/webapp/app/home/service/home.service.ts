import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ResponseData } from '../ResponseData.model';

export type EntityArrayResponseType = HttpResponse<ResponseData[]>;

@Injectable({
  providedIn: 'root',
})
export class HomeService {
  protected elasticSearchUrl = this.applicationConfigService.getEndpointFor('api/_search-all', 'aladintechcobackendtest');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  elasticSearchAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ResponseData[]>(this.elasticSearchUrl, { params: options, observe: 'response' });
  }
}

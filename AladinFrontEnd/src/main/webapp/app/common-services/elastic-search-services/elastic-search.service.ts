import { ResponseData } from 'app/dto-models/responseData.model';
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmployee } from 'app/entities/employee/employee.model';

export type EntityArrayResponseType = HttpResponse<ResponseData[]>;
export type EmployeeArrayResponseType = HttpResponse<IEmployee[]>;

@Injectable({
  providedIn: 'root',
})
export class ElasticSearchService {
  protected elasticSearchAllUrl = this.applicationConfigService.getEndpointFor('api/_search-all', 'aladintechcobackendtest');
  protected elasticSearchEmployeeUrl = this.applicationConfigService.getEndpointFor('api/_search-employee', 'aladintechcobackendtest');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  elasticSearchAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ResponseData[]>(this.elasticSearchAllUrl, { params: options, observe: 'response' });
  }

  elasticSearchEmployee(req?: any): Observable<EmployeeArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmployee[]>(this.elasticSearchEmployeeUrl, { params: options, observe: 'response' });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { Observable } from 'rxjs';
import { IContact } from './contact-page.model';
import { IService } from '../../service/service.model';
import { Pagination } from '../../core/request/request.model';
import { createRequestOption } from '../../core/request/request-util';

@Injectable({ providedIn: 'root' })
export class ContactPageService {
  // private resourceUrl = this.applicationConfigService.getEndpointFor('api/contact');
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(contact: IContact): Observable<{}> {
    return this.http.post(this.applicationConfigService.getEndpointFor('api/contacts', 'aladintechcobackendtest'), contact);
  }

  query(req?: Pagination): Observable<HttpResponse<IService[]>> {
    const options = createRequestOption(req);
    return this.http.get<IService[]>(this.applicationConfigService.getEndpointFor('api/services', 'aladintechcobackendtest'), {
      params: options,
      observe: 'response',
    });
  }
}

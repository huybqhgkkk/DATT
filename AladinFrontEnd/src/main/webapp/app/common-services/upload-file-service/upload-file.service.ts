import { HttpClient, HttpEvent, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { Observable } from 'rxjs';
export type EntityArrayResponseType = HttpResponse<any>;
@Injectable({
  providedIn: 'root',
})
export class UploadFileService {
  protected uploadFileUrl = this.applicationConfigService.getEndpointFor('api/avatar', 'aladintechcobackendtest');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  uploadFile(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('image', file);
    return this.http.post<any>(this.uploadFileUrl, formData, { responseType: 'text' as 'json' });
  }
}

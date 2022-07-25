import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ApplicationConfigService {
  private endpointPrefix = '';

  setEndpointPrefix(endpointPrefix: string): void {
    this.endpointPrefix = endpointPrefix;
    console.log(" ;set endpointPrefix:",endpointPrefix);
  }

  getEndpointFor(api: string, microservice?: string): string {
    console.log("api:",api , ";microservice:",microservice ," ;endpointPrefix:",this.endpointPrefix.toString());
    if (microservice) {
      const endpoint=`${this.endpointPrefix}services/${microservice}/${api}`;
      console.log("micro endpoint:",endpoint);
      return endpoint;
    }
    const endpoint=`${this.endpointPrefix}${api}`;
    console.log("endpoint:",endpoint);
    return endpoint;
  }
}

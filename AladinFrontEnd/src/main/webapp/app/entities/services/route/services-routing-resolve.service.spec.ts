jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IServices, Services } from '../services.model';
import { ServicesService } from '../service/services.service';

import { ServicesRoutingResolveService } from './services-routing-resolve.service';

describe('Service Tests', () => {
  describe('Services routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ServicesRoutingResolveService;
    let service: ServicesService;
    let resultServices: IServices | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ServicesRoutingResolveService);
      service = TestBed.inject(ServicesService);
      resultServices = undefined;
    });

    describe('resolve', () => {
      it('should return IServices returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultServices = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultServices).toEqual({ id: 123 });
      });

      it('should return new IServices if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultServices = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultServices).toEqual(new Services());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Services })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultServices = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultServices).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});

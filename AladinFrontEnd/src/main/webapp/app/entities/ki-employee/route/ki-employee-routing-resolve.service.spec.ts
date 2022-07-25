jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IKiEmployee, KiEmployee } from '../ki-employee.model';
import { KiEmployeeService } from '../service/ki-employee.service';

import { KiEmployeeRoutingResolveService } from './ki-employee-routing-resolve.service';

describe('Service Tests', () => {
  describe('KiEmployee routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: KiEmployeeRoutingResolveService;
    let service: KiEmployeeService;
    let resultKiEmployee: IKiEmployee | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(KiEmployeeRoutingResolveService);
      service = TestBed.inject(KiEmployeeService);
      resultKiEmployee = undefined;
    });

    describe('resolve', () => {
      it('should return IKiEmployee returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultKiEmployee = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultKiEmployee).toEqual({ id: 123 });
      });

      it('should return new IKiEmployee if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultKiEmployee = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultKiEmployee).toEqual(new KiEmployee());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as KiEmployee })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultKiEmployee = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultKiEmployee).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});

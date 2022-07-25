jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IViettelSubscriber2, ViettelSubscriber2 } from '../viettel-subscriber-2.model';
import { ViettelSubscriber2Service } from '../service/viettel-subscriber-2.service';

import { ViettelSubscriber2RoutingResolveService } from './viettel-subscriber-2-routing-resolve.service';

describe('Service Tests', () => {
  describe('ViettelSubscriber2 routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ViettelSubscriber2RoutingResolveService;
    let service: ViettelSubscriber2Service;
    let resultViettelSubscriber2: IViettelSubscriber2 | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ViettelSubscriber2RoutingResolveService);
      service = TestBed.inject(ViettelSubscriber2Service);
      resultViettelSubscriber2 = undefined;
    });

    describe('resolve', () => {
      it('should return IViettelSubscriber2 returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultViettelSubscriber2 = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultViettelSubscriber2).toEqual({ id: 123 });
      });

      it('should return new IViettelSubscriber2 if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultViettelSubscriber2 = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultViettelSubscriber2).toEqual(new ViettelSubscriber2());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultViettelSubscriber2 = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultViettelSubscriber2).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});

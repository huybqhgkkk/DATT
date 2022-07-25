jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IViettelSubscriber, ViettelSubscriber } from '../viettel-subscriber.model';
import { ViettelSubscriberService } from '../service/viettel-subscriber.service';

import { ViettelSubscriberRoutingResolveService } from './viettel-subscriber-routing-resolve.service';

describe('Service Tests', () => {
  describe('ViettelSubscriber routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ViettelSubscriberRoutingResolveService;
    let service: ViettelSubscriberService;
    let resultViettelSubscriber: IViettelSubscriber | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ViettelSubscriberRoutingResolveService);
      service = TestBed.inject(ViettelSubscriberService);
      resultViettelSubscriber = undefined;
    });

    describe('resolve', () => {
      it('should return IViettelSubscriber returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultViettelSubscriber = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultViettelSubscriber).toEqual({ id: 123 });
      });

      it('should return new IViettelSubscriber if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultViettelSubscriber = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultViettelSubscriber).toEqual(new ViettelSubscriber());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultViettelSubscriber = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultViettelSubscriber).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});

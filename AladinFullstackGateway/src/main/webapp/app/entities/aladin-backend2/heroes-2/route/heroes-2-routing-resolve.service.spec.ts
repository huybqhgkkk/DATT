jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IHeroes2, Heroes2 } from '../heroes-2.model';
import { Heroes2Service } from '../service/heroes-2.service';

import { Heroes2RoutingResolveService } from './heroes-2-routing-resolve.service';

describe('Service Tests', () => {
  describe('Heroes2 routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: Heroes2RoutingResolveService;
    let service: Heroes2Service;
    let resultHeroes2: IHeroes2 | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(Heroes2RoutingResolveService);
      service = TestBed.inject(Heroes2Service);
      resultHeroes2 = undefined;
    });

    describe('resolve', () => {
      it('should return IHeroes2 returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHeroes2 = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHeroes2).toEqual({ id: 123 });
      });

      it('should return new IHeroes2 if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHeroes2 = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultHeroes2).toEqual(new Heroes2());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHeroes2 = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHeroes2).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});

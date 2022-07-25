jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IHeroes3, Heroes3 } from '../heroes-3.model';
import { Heroes3Service } from '../service/heroes-3.service';

import { Heroes3RoutingResolveService } from './heroes-3-routing-resolve.service';

describe('Service Tests', () => {
  describe('Heroes3 routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: Heroes3RoutingResolveService;
    let service: Heroes3Service;
    let resultHeroes3: IHeroes3 | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(Heroes3RoutingResolveService);
      service = TestBed.inject(Heroes3Service);
      resultHeroes3 = undefined;
    });

    describe('resolve', () => {
      it('should return IHeroes3 returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHeroes3 = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHeroes3).toEqual({ id: 123 });
      });

      it('should return new IHeroes3 if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHeroes3 = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultHeroes3).toEqual(new Heroes3());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHeroes3 = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHeroes3).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});

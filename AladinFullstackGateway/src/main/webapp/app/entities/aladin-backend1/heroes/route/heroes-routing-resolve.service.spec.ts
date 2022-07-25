jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IHeroes, Heroes } from '../heroes.model';
import { HeroesService } from '../service/heroes.service';

import { HeroesRoutingResolveService } from './heroes-routing-resolve.service';

describe('Service Tests', () => {
  describe('Heroes routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: HeroesRoutingResolveService;
    let service: HeroesService;
    let resultHeroes: IHeroes | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(HeroesRoutingResolveService);
      service = TestBed.inject(HeroesService);
      resultHeroes = undefined;
    });

    describe('resolve', () => {
      it('should return IHeroes returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHeroes = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHeroes).toEqual({ id: 123 });
      });

      it('should return new IHeroes if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHeroes = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultHeroes).toEqual(new Heroes());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHeroes = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHeroes).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});

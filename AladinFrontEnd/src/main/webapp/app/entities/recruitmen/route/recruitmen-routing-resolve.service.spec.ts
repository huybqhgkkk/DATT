jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IRecruitmen, Recruitmen } from '../recruitmen.model';
import { RecruitmenService } from '../service/recruitmen.service';

import { RecruitmenRoutingResolveService } from './recruitmen-routing-resolve.service';

describe('Service Tests', () => {
  describe('Recruitmen routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: RecruitmenRoutingResolveService;
    let service: RecruitmenService;
    let resultRecruitmen: IRecruitmen | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(RecruitmenRoutingResolveService);
      service = TestBed.inject(RecruitmenService);
      resultRecruitmen = undefined;
    });

    describe('resolve', () => {
      it('should return IRecruitmen returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRecruitmen = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultRecruitmen).toEqual({ id: 123 });
      });

      it('should return new IRecruitmen if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRecruitmen = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultRecruitmen).toEqual(new Recruitmen());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Recruitmen })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRecruitmen = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultRecruitmen).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});

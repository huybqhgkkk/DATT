jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEmailtemplate, Emailtemplate } from '../emailtemplate.model';
import { EmailtemplateService } from '../service/emailtemplate.service';

import { EmailtemplateRoutingResolveService } from './emailtemplate-routing-resolve.service';

describe('Emailtemplate routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EmailtemplateRoutingResolveService;
  let service: EmailtemplateService;
  let resultEmailtemplate: IEmailtemplate | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(EmailtemplateRoutingResolveService);
    service = TestBed.inject(EmailtemplateService);
    resultEmailtemplate = undefined;
  });

  describe('resolve', () => {
    it('should return IEmailtemplate returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEmailtemplate = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEmailtemplate).toEqual({ id: 123 });
    });

    it('should return new IEmailtemplate if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEmailtemplate = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEmailtemplate).toEqual(new Emailtemplate());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Emailtemplate })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEmailtemplate = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEmailtemplate).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

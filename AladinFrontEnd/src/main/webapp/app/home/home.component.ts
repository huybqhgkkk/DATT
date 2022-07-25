import { ResponseData } from 'app/dto-models/responseData.model';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { ServicesService } from '../entities/services/service/services.service';
import { HttpResponse } from '@angular/common/http';
import { IServices } from '../entities/services/services.model';
import { NgbCarousel } from '@ng-bootstrap/ng-bootstrap';
import { ElasticSearchService } from 'app/common-services/elastic-search-services/elastic-search.service';

declare const $: any;

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  @ViewChild('carousel', { static: true }) carousel!: NgbCarousel;
  listServices: IServices[] | null = null;
  account: Account | null = null;
  searchText = '';
  typingTimer: any;
  responseData!: ResponseData[];
  private readonly destroy$ = new Subject<void>();

  constructor(
    private accountService: AccountService,
    private router: Router,
    private servicesService: ServicesService,
    private homeService: ElasticSearchService
  ) {}

  onKeyDown(): void {
    clearTimeout(this.typingTimer);
  }

  onKeyUp(): void {
    this.search();
  }

  inFocus(): void {
    this.carousel.pause();
  }

  outFocus(): void {
    this.carousel.cycle();
  }

  navigateToSearch(): void {
    if (this.searchText.length > 0) {
      this.router.navigateByUrl(`/search-all?search=${this.searchText}`);
    }
  }

  search(): void {
    if (this.searchText.length > 0) {
      this.homeService
        .elasticSearchAll({
          query: '*' + this.searchText + '*',
          page: 0,
          size: 5,
        })
        .subscribe(res => {
          this.responseData = res.body as ResponseData[];
          if (this.responseData.length > 0) {
            $('.result-area').css({
              display: 'block',
            });
            $('.search-input').css({
              borderRadius: '10px 10px 0 0',
            });
          }
        });
    } else {
      $('.search-input').css({
        borderRadius: '10px',
      });
      $('.result-area').css({
        display: 'none',
      });
    }
  }

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
    this.servicesService.query().subscribe((res: HttpResponse<IServices[]>) => {
      this.listServices = res.body;
    });
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}

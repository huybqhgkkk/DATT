import { ResponseData } from 'app/dto-models/responseData.model';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ITEMS_PER_PAGE } from '../config/pagination.constants';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { combineLatest } from 'rxjs';
import { ElasticSearchService } from 'app/common-services/elastic-search-services/elastic-search.service';

@Component({
  selector: 'jhi-intermediary',
  templateUrl: './intermediary.component.html',
  styleUrls: ['./intermediary.component.css'],
})
export class IntermediaryComponent implements OnInit {
  isLoading = false;
  searchText = '';
  listResponseData!: ResponseData[];
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page?: number;
  ngbPaginationPage = 1;
  hasData = false;
  loadPageEvent!: boolean;
  typingTimer: any;

  searchForm = this.fb.group({
    searchValue: ['', Validators.required],
  });

  constructor(
    private fb: FormBuilder,
    private searchService: ElasticSearchService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 1;
    this.searchService
      .elasticSearchAll({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        query: `*${this.searchText}*`,
      })
      .subscribe((res: HttpResponse<ResponseData[]>) => {
        this.isLoading = false;
        this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
      });
  }

  onSearch(): void {
    if (this.searchForm.value.searchValue) {
      this.page = 1;
      this.search();
    }
  }

  search(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page ?? this.page ?? 1;
    this.isLoading = true;
    this.searchText = this.searchForm.value.searchValue;
    this.searchService
      .elasticSearchAll({
        query: `*${this.searchText}*`,
        page: pageToLoad - 1,
        size: this.itemsPerPage,
      })
      .subscribe(
        (res: HttpResponse<ResponseData[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
        },
        () => {
          this.isLoading = false;
          this.onError();
        }
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
  }

  protected handleNavigation(): void {
    combineLatest([this.route.data, this.route.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      this.searchText = params.get('search') as string;
      console.log('params:', params);
      this.searchForm.patchValue({
        searchValue: this.searchText,
      });
      const pageNumber = +(page ?? 1);
      if (pageNumber !== this.page) {
        this.loadPage(pageNumber, true);
      }
    });
  }

  protected onSuccess(data: ResponseData[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      if (this.searchText) {
        this.router.navigateByUrl(`/search-all?search=${this.searchText}&page=${this.page}&size=${this.itemsPerPage}`);
      } else {
        this.router.navigateByUrl(`/search-all`);
      }
    }
    this.listResponseData = data ?? [];
    if (this.listResponseData.length > 0) {
      this.hasData = true;
    } else {
      this.hasData = false;
    }
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}

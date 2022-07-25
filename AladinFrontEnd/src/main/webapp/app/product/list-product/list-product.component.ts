import { Component, OnInit } from '@angular/core';
import { IProduct, Product } from '../product.model';
import { ProductService } from '../product.service';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from '../../config/pagination.constants';
import { combineLatest } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../../admin/user-management/user-management.model';

@Component({
  selector: 'jhi-list-product',
  templateUrl: './list-product.component.html',
  styleUrls: ['./list-product.component.scss'],
})
export class ListProductComponent implements OnInit {
  isLoading = false;
  listProduct: IProduct[] | null = null;

  totalItems = 0;
  itemsPerPage = 3;
  page!: number;
  predicate!: string;
  ascending!: boolean;

  constructor(protected productService: ProductService, private activatedRoute: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    // this.handleNavigation();
    this.loadAll();
  }

  loadAll(): void {
    //   this.productService.query({
    //     page: this.page-1,
    //     size: this.itemsPerPage,
    //     sort: this.sort(),
    // }).subscribe(
    //     (res: HttpResponse<IProduct[]>) => {
    //       this.isLoading = true;
    //       this.onSuccess(res.body,res.headers)
    //     },
    //     () => (this.isLoading = false)
    //   );

    this.productService.query().subscribe(
      (res: HttpResponse<any[]>) => {
        this.listProduct = res.body;
        this.isLoading = true;
      },
      () => (this.isLoading = false)
    );
  }

  transition(): void {
    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        sort: this.predicate + ',' + (this.ascending ? ASC : DESC),
      },
    });
  }

  private sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private onSuccess(products: Product[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.listProduct = products;
  }

  private handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      this.page = page !== null ? +page : 1;
      // const sort = (params.get(SORT) ?? data['defaultSort']).split(',');
      // this.predicate = sort[0];
      // this.ascending = sort[1] === ASC;
      this.loadAll();
    });
  }
}

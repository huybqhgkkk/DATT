import { Component, ElementRef, HostListener, OnInit } from '@angular/core';
import { IProduct } from '../product.model';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../product.service';
import { HttpResponse } from '@angular/common/http';
import { IService } from '../../service/service.model';
import { ServiceService } from '../../service/service-entity.service';
import { FormBuilder } from '@angular/forms';
import { DataUtils, FileLoadError } from '../../core/util/data-util.service';
import { EventManager, EventWithContent } from '../../core/util/event-manager.service';
import { window } from 'rxjs/operators';

@Component({
  selector: 'jhi-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.scss'],
})
export class ProductDetailComponent implements OnInit {
  serviceDetail: IService | null = null;
  productDetail: IProduct | null = null;
  productId: any;
  isLoading = false;
  test = '';
  listContent: string[] | undefined = undefined;
  fontBold = { fontWeight: 'bold' };
  fontIndented = { textIndent: '0' };
  isSaving = false;
  backgroundSer: any;
  ok: any;
  listProducts: IProduct[] | undefined = undefined;
  benefit = {
    content:
      'Các cán bộ Aladin có nhiều năm kinh nghiệm và đóng vai trò quan trọng triển khai các hệ thống phần mềm tại các công ty viễn thông',
    arrReason: [
      {
        contentReason: 'Quản lý phát triển sản phẩm liên tục, đề xuất các tính năng, giải pháp mới ',
        iconReason: 'manager.png',
      },
      {
        contentReason: 'Cung cấp các nguồn lực và kiến thức chuyên môn cần thiết, hoàn thành dự án nhanh chóng',
        iconReason: 'fast.png',
      },
      {
        contentReason: 'Bảo mật thông tin hàng đầu',
        iconReason: 'security.png',
      },
      {
        contentReason: 'Cải thiện hiệu quả hoạt động, đảm bảo các tiêu chí cho các giải pháp. ',
        iconReason: 'advance.png',
      },
    ],
  };

  constructor(
    protected productService: ProductService,
    private route: ActivatedRoute,
    protected serviceService: ServiceService,
    protected fb: FormBuilder,
    protected elementRef: ElementRef,
    protected dataUtils: DataUtils,
    protected eventManager: EventManager
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(pr => {
      this.productId = pr.get('id');
    });

    this.productService.find(this.productId).subscribe(
      (res: HttpResponse<IProduct>) => {
        this.productDetail = res.body;
        this.isLoading = true;
        if (this.productDetail !== null) {
          this.listContent = this.productDetail.content?.split('\n');
          this.test = String(this.productDetail.name);
          this.test = this.test.replace(' ', '%20');
          this.backgroundSer = { backgroundImage: `url(../../content/images/products/background${this.test.toUpperCase()}.png)` };
        }
      },
      () => (this.isLoading = false)
    );

    this.productService.query().subscribe(res => {
      this.listProducts = res.body?.slice(0, 15);
    });
  }

  //image

  save(): void {
    this.isLoading = true;
  }

  onScroll(): void {
    console.log(document.body.scrollTop);
    document.querySelector('#ok1')?.scrollIntoView();
    // window.scrollBy(0, document.querySelector('.background-img')?.scrollHeight);
  }
}

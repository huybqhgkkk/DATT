import { Component, OnInit } from '@angular/core';
import { IService } from '../service.model';
import { ActivatedRoute } from '@angular/router';
import { ServiceService } from '../service-entity.service';
import { HttpResponse } from '@angular/common/http';
import { ProductService } from '../../product/product.service';
import { IProduct } from '../../entities/product/product.model';

@Component({
  selector: 'jhi-service-detail',
  templateUrl: './service-detail.component.html',
  styleUrls: ['./service-detail.component.scss'],
})
export class ServiceDetailComponent implements OnInit {
  serviceDetail: IService | null = null;
  isLoading = false;
  test = '';
  backgroundSer: any;
  ok: any;
  listProducts: IProduct[] | null = null;

  benefit = [
    {
      id: 'VAS',
      content:
        'Đội ngũ Nhân viên có kinh ngiệm trong quá trình phát triển và nâng cấp dịch vụ Vas trong từng thời kỳ công nghệ khác nhau để phù hợp với nhu cầu.',
      arrReason: [
        {
          contentReason: 'Cung cấp đúng giá trị để đáp ứng nhu cầu thực tại của khách hàng trên thị trường',
          iconReason: 'execute.png',
        },
        {
          contentReason: 'Tổ chức đội ngũ quản lý giúp sản phẩm vận hành tốt nhất',
          iconReason: 'positive.png',
        },
        {
          contentReason: 'Sản phẩm sáng tạo và thân thiện với người dùng',
          iconReason: 'exp.png',
        },
      ],
    },
    {
      id: 'Platform',
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
    },
    {
      id: 'Outsource',
      content: 'Đội ngũ cán bộ có thái độ tích cực, có trình độ và kỹ năng phù hợp với từng vị trí.',
      arrReason: [
        {
          contentReason: 'Đẩy nhanh tiến độ hoàn thành công việc',
          iconReason: 'positive.png',
        },
        {
          contentReason: 'Tiếp cận các nguồn công nghệ hiện đại',
          iconReason: 'service.png',
        },
        {
          contentReason: 'Nâng cao chất lượng, hiệu suất lao động cao, tiết kiệm thời gian, chi phí',
          iconReason: 'advance.png',
        },
      ],
    },
  ];

  overview = [
    {
      id: 'VAS',
      arrFeature: [
        {
          content:
            'Cung cấp và triển khai các dịch vụ VAS cho các nhà mạng Viettel, VinaPhone, Mobifone tại Việt Nam và trên thế giới  (Bitel, StarTelecom, Halotel, MyTel...)',
          imgFeature: 'VAS1.png',
        },
        {
          content: 'Đa dạng hóa, đưa ra dịch vụ tốt nhất trên cơ sở phân tích nhu cầu của thị trường và xu hướng công nghệ mới',
          imgFeature: 'VAS.png',
        },
      ],
    },
    {
      id: 'Platform',
      arrFeature: [
        {
          content: 'Triển khai hệ thống core và tính cước cho các dịch vụ VAS',
          imgFeature: 'Platform1.png',
        },
        {
          content: 'Phát triển fullstack viễn thông, giao tiếp với các tổng đài core của nhà mạng',
          imgFeature: 'VAS2.png',
        },
      ],
    },
    {
      id: 'Outsource',
      arrFeature: [
        {
          content: 'Phát triển mở rộng hệ thống, đáp ứng triệt để các yêu cầu của khách hàng với chi phí hợp lý',
          imgFeature: 'outsource1.png',
        },
        {
          content: 'Cung cấp đa dạng các giải pháp cho các doanh nghiệp lớn trong và ngoài nước ',
          imgFeature: 'outsource2.png',
        },
      ],
    },
  ];
  constructor(
    protected activatedRoute: ActivatedRoute,
    protected serviceService: ServiceService,
    private route: ActivatedRoute,
    protected productService: ProductService
  ) {}

  ngOnInit(): void {
    // this.activatedRoute.data.subscribe(({listService}) => {
    //   this.serviceDetail = listService,
    //     this.test=listService.type;
    // })
    this.route.paramMap.subscribe(pr => {
      this.ok = pr.get('id');
      this.serviceService.findProductByServicesId(this.ok).subscribe(res => {
        this.listProducts = res.body;
      });
      this.serviceService.find(this.ok).subscribe((res: HttpResponse<IService>) => {
        this.serviceDetail = res.body;
        if (this.serviceDetail !== null) {
          this.test = String(this.serviceDetail.type);
          this.test = this.test.replace(' ', '%20');
          this.backgroundSer = { backgroundImage: `url(../../content/images/services/Header${this.test}.png)` };
        }
      });
    });
  }
}

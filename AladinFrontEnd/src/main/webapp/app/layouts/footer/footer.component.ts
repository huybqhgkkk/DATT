import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../../service/service-entity.service';
import { IService } from '../../service/service.model';

@Component({
  selector: 'jhi-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['../navbar/navbar.component.scss'],
})
export class FooterComponent implements OnInit {
  serviceList: IService[] | undefined = undefined;
  constructor(protected serviceService: ServiceService) {}
  ngOnInit(): void {
    this.serviceService.query().subscribe(res => {
      this.serviceList = res.body?.slice(0, 4);
    });
  }
}

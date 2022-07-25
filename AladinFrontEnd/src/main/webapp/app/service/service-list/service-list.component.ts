import { Component, OnInit } from '@angular/core';
import { IService } from '../service.model';
import { ServiceService } from '../service-entity.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-service-list',
  templateUrl: './service-list.component.html',
  styleUrls: ['./service-list.component.scss'],
})
export class ServiceListComponent implements OnInit {
  listService: IService[] | null = null;
  isLoading = false;
  constructor(protected serviceService: ServiceService) {}
  ngOnInit(): void {
    this.loadAll();
  }
  loadAll(): void {
    this.isLoading = true;

    this.serviceService.query().subscribe(
      (res: HttpResponse<IService[]>) => {
        this.isLoading = false;
        this.listService = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }
}

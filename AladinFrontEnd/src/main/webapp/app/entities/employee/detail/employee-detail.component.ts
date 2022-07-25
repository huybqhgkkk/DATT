import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmployee } from '../employee.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { HttpResponse } from '@angular/common/http';
import { IDepartment } from '../../department/department.model';
import { DepartmentService } from '../../department/service/department.service';
import { ITEMS_PER_PAGE } from '../../../config/pagination.constants';

@Component({
  selector: 'jhi-employee-detail',
  templateUrl: './employee-detail.component.html',
  styleUrls: ['./employee-detail.css'],
})
export class EmployeeDetailComponent implements OnInit {
  employee: IEmployee | null = null;
  itemsPerPage = ITEMS_PER_PAGE;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute, protected departmentService: DepartmentService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.employee = employee;
    });

    this.departmentService
      .query({
        size: this.itemsPerPage,
      })
      .subscribe((res: HttpResponse<IDepartment[]>) => {
        console.log();
      });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}

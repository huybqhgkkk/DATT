import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepartment } from '../department.model';
import { DepartmentService } from '../service/department.service';

@Component({
  selector: 'jhi-department-detail',
  templateUrl: './department-detail.component.html',
  styleUrls: ['./department-detail.component.css']
})
export class DepartmentDetailComponent implements OnInit {
  departments: IDepartment[] | null = null;

  constructor(protected activatedRoute: ActivatedRoute, protected departmentService: DepartmentService) {}

  ngOnInit(): void {
    const deparmentName = this.activatedRoute.snapshot.paramMap.get('departmentsname');
    this.departmentService.findPB(deparmentName as string).subscribe(res => {
      this.departments = res.body as IDepartment[];
    });
  }

  previousState(): void {
    window.history.back();
  }
}

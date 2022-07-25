import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKiEmployee } from '../ki-employee.model';

@Component({
  selector: 'jhi-ki-employee-detail',
  templateUrl: './ki-employee-detail.component.html',
  styleUrls: ['./ki-employee-detail.css']
})
export class KiEmployeeDetailComponent implements OnInit {
  kiEmployee: IKiEmployee | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kiEmployee }) => {
      this.kiEmployee = kiEmployee;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

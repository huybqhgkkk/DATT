import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IKiEmployee, KiEmployee } from '../ki-employee.model';
import { KiEmployeeService } from '../service/ki-employee.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import * as moment from 'moment';

@Component({
  selector: 'jhi-ki-employee-update',
  templateUrl: './ki-employee-update.component.html',
  styleUrls: ['./ki-employee-update.css'],
})
export class KiEmployeeUpdateComponent implements OnInit {
  isSaving = false;
  Datetime = moment(new Date()).format('yyyy-MM-DD');
  employeesSharedCollection: IEmployee[] = [];
  point = 1.0;
  number = 2.0;

  editForm = this.fb.group({
    id: [],
    date_time: [null, [Validators.required]],
    work_quantity: [null, [Validators.required, Validators.min(0.1), Validators.max(2.0)]],
    work_quantity_comment: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(2000)]],
    work_quality: [null, [Validators.required, Validators.min(0.1), Validators.max(2.0)]],
    work_quality_comment: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(2000)]],
    work_progress: [null, [Validators.required, Validators.min(0.1), Validators.max(2.0)]],
    work_progress_comment: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(2000)]],
    work_attitude: [null, [Validators.required, Validators.min(0.1), Validators.max(2.0)]],
    work_attitude_comment: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(2000)]],
    work_discipline: [null, [Validators.required, Validators.min(0.1), Validators.max(2.0)]],
    work_discipline_comment: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(2000)]],
    assigned_work: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(2000)]],
    other_work: [null, [Validators.required, Validators.maxLength(2000)]],
    completed_work: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(2000)]],
    uncompleted_work: [null, [Validators.maxLength(2000)]],
    favourite_work: [null, [Validators.required, Validators.maxLength(2000)]],
    unfavourite_work: [null, [Validators.maxLength(2000)]],
    employee_ki_point: [null, [Validators.required, Validators.min(0.1), Validators.max(2.0)]],
    leader_ki_point: [null, [Validators.required, Validators.min(0.1), Validators.max(2.0)]],
    leader_comment: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(2000)]],
    boss_ki_point: [null, [Validators.required, Validators.min(0.1), Validators.max(2.0)]],
    boss_comment: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(2000)]],
    employee: [null, Validators.required],
  });

  constructor(
    protected kiEmployeeService: KiEmployeeService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.loadRelationshipsOptions();
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kiEmployee = this.createFromForm();
    if (kiEmployee.id !== undefined) {
      this.subscribeToSaveResponse(this.kiEmployeeService.update(kiEmployee));
    } else {
      this.subscribeToSaveResponse(this.kiEmployeeService.create(kiEmployee));
    }
  }

  trackEmployeeById(index: number, item: IEmployee): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKiEmployee>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(kiEmployee: IKiEmployee): void {
    this.editForm.patchValue({
      id: kiEmployee.id,
      date_time: kiEmployee.date_time,
      work_quantity: kiEmployee.work_quantity,
      work_quantity_comment: kiEmployee.work_quantity_comment,
      work_quality: kiEmployee.work_quality,
      work_quality_comment: kiEmployee.work_quality_comment,
      work_progress: kiEmployee.work_progress,
      work_progress_comment: kiEmployee.work_progress_comment,
      work_attitude: kiEmployee.work_attitude,
      work_attitude_comment: kiEmployee.work_attitude_comment,
      work_discipline: kiEmployee.work_discipline,
      work_discipline_comment: kiEmployee.work_discipline_comment,
      assigned_work: kiEmployee.assigned_work,
      other_work: kiEmployee.other_work,
      completed_work: kiEmployee.completed_work,
      uncompleted_work: kiEmployee.uncompleted_work,
      favourite_work: kiEmployee.favourite_work,
      unfavourite_work: kiEmployee.unfavourite_work,
      employee_ki_point: kiEmployee.employee_ki_point,
      leader_ki_point: kiEmployee.leader_ki_point,
      leader_comment: kiEmployee.leader_comment,
      boss_ki_point: kiEmployee.boss_ki_point,
      boss_comment: kiEmployee.boss_comment,
      employee: kiEmployee.employee?.full_name,
    });

    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing(
      this.employeesSharedCollection,
      kiEmployee.employee
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .queryKI()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing(employees, this.editForm.get('employee')!.value)
        )
      )
      .subscribe((employees: IEmployee[]) => {
        this.employeesSharedCollection = employees;
        this.activatedRoute.data.subscribe(({ kiEmployee }) => {
          this.updateForm(kiEmployee);
        });
      });
  }

  protected createFromForm(): IKiEmployee {
    return {
      ...new KiEmployee(),
      id: this.editForm.get(['id'])!.value,
      date_time: this.editForm.get(['date_time'])!.value,
      work_quantity: this.editForm.get(['work_quantity'])!.value,
      work_quantity_comment: this.editForm.get(['work_quantity_comment'])!.value,
      work_quality: this.editForm.get(['work_quality'])!.value,
      work_quality_comment: this.editForm.get(['work_quality_comment'])!.value,
      work_progress: this.editForm.get(['work_progress'])!.value,
      work_progress_comment: this.editForm.get(['work_progress_comment'])!.value,
      work_attitude: this.editForm.get(['work_attitude'])!.value,
      work_attitude_comment: this.editForm.get(['work_attitude_comment'])!.value,
      work_discipline: this.editForm.get(['work_discipline'])!.value,
      work_discipline_comment: this.editForm.get(['work_discipline_comment'])!.value,
      assigned_work: this.editForm.get(['assigned_work'])!.value,
      other_work: this.editForm.get(['other_work'])!.value,
      completed_work: this.editForm.get(['completed_work'])!.value,
      uncompleted_work: this.editForm.get(['uncompleted_work'])!.value,
      favourite_work: this.editForm.get(['favourite_work'])!.value,
      unfavourite_work: this.editForm.get(['unfavourite_work'])!.value,
      employee_ki_point: this.editForm.get(['employee_ki_point'])!.value,
      leader_ki_point: this.editForm.get(['leader_ki_point'])!.value,
      leader_comment: this.editForm.get(['leader_comment'])!.value,
      boss_ki_point: this.editForm.get(['boss_ki_point'])!.value,
      boss_comment: this.editForm.get(['boss_comment'])!.value,
      employee: this.employeesSharedCollection[0],
      status: '1',
    };
  }
}

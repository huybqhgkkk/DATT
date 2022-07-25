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
import * as dayjs from 'dayjs';
@Component({
  selector: 'jhi-ki-employee-update',
  templateUrl: './ki-employee-create.component.html',
  styleUrls: ['./ki-employee-create.css'],
})
export class kiEmployeeCreateComponent implements OnInit {
  isSaving = false;
  Datetime = moment(new Date()).format('yyyy-MM-DD');
  employeesSharedCollection: IEmployee[] = [];
  point = 1.0;
  NoteGD = 'Đánh giá của giám đốc';
  NoteTP = 'Đánh giá của trưởng phòng';

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
    other_work: [null, [Validators.required, Validators.maxLength(500)]],
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

  protected createForm(kiEmployee: IKiEmployee): void {
    this.editForm.patchValue({
      id: kiEmployee.id,
      date_time: this.Datetime,
      work_quantity: this.point,
      work_quantity_comment: null,
      work_quality: this.point,
      work_quality_comment: null,
      work_progress: this.point,
      work_progress_comment: null,
      work_attitude: this.point,
      work_attitude_comment: null,
      work_discipline: this.point,
      work_discipline_comment: null,
      assigned_work: null,
      other_work: null,
      completed_work: null,
      uncompleted_work: null,
      favourite_work: null,
      unfavourite_work: null,
      employee_ki_point: this.point,
      leader_ki_point: this.point,
      leader_comment: this.NoteTP,
      boss_ki_point: this.point,
      boss_comment: this.NoteGD,
      employee: this.employeesSharedCollection[0].full_name,
    });
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
          this.createForm(kiEmployee);
        });
      });
  }

  protected createFromForm(): IKiEmployee {
    return {
      ...new KiEmployee(),
      id: this.editForm.get(['id'])!.value,
      date_time: dayjs(this.editForm.get(['date_time'])!.value),
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

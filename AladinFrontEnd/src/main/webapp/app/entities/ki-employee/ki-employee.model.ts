import * as dayjs from 'dayjs';
import { IEmployee } from 'app/entities/employee/employee.model';

export interface IKiEmployee {
  id?: number;
  date_time?: dayjs.Dayjs;
  work_quantity?: number;
  work_quantity_comment?: string;
  work_quality?: number;
  work_quality_comment?: string;
  work_progress?: number;
  work_progress_comment?: string;
  work_attitude?: number;
  work_attitude_comment?: string;
  work_discipline?: number;
  work_discipline_comment?: string;
  assigned_work?: string;
  other_work?: string;
  completed_work?: string;
  uncompleted_work?: string | null;
  favourite_work?: string;
  unfavourite_work?: string | null;
  employee_ki_point?: number;
  leader_ki_point?: number;
  leader_comment?: string;
  boss_ki_point?: number;
  boss_comment?: string;
  status?: string | null;
  full_name?: string;
  employee?: IEmployee;
}

export class KiEmployee implements IKiEmployee {
  constructor(
    public id?: number,
    public date_time?: dayjs.Dayjs,
    public work_quantity?: number,
    public work_quantity_comment?: string,
    public work_quality?: number,
    public work_quality_comment?: string,
    public work_progress?: number,
    public work_progress_comment?: string,
    public work_attitude?: number,
    public work_attitude_comment?: string,
    public work_discipline?: number,
    public work_discipline_comment?: string,
    public assigned_work?: string,
    public other_work?: string,
    public completed_work?: string,
    public uncompleted_work?: string | null,
    public favourite_work?: string,
    public unfavourite_work?: string | null,
    public employee_ki_point?: number,
    public leader_ki_point?: number,
    public leader_comment?: string,
    public boss_ki_point?: number,
    public boss_comment?: string,
    public status?: string | null,
    public full_name?: string,
    public employee?: IEmployee
  ) {}
}

export function getKiEmployeeIdentifier(kiEmployee: IKiEmployee): number | undefined {
  return kiEmployee.id;
}

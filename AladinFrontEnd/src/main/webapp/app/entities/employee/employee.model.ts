import * as dayjs from 'dayjs';
import { IUser } from 'app/entities/user/user.model';
import { IDepartment } from 'app/entities/department/department.model';

export interface IEmployee {
  id?: number;
  first_day_work?: dayjs.Dayjs;
  full_name?: string;
  phone_number?: string;
  email?: string;
  date_of_birth?: dayjs.Dayjs;
  countryside?: string;
  current_residence?: string;
  relative?: string;
  favourite?: string;
  education?: string;
  experience?: string;
  english?: string;
  objective_in_cv?: string;
  marital_status?: string;
  children?: string;
  family?: string;
  avatar?: string;
  gender?: string;
  certificationContentType?: string | null;
  certification?: string | null;
  bank_name?: string;
  account_number?: string;
  user?: IUser;
  department?: IDepartment;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public first_day_work?: dayjs.Dayjs,
    public full_name?: string,
    public phone_number?: string,
    public email?: string,
    public date_of_birth?: dayjs.Dayjs,
    public countryside?: string,
    public current_residence?: string,
    public relative?: string,
    public favourite?: string,
    public education?: string,
    public experience?: string,
    public english?: string,
    public objective_in_cv?: string,
    public marital_status?: string,
    public children?: string,
    public family?: string,
    public avatar?: string,
    public gender?: string,
    public certificationContentType?: string | null,
    public certification?: string | null,
    public bank_name?: string,
    public account_number?: string,

    public user?: IUser,
    public department?: IDepartment
  ) {}
}

export function getEmployeeIdentifier(employee: IEmployee): number | undefined {
  return employee.id;
}

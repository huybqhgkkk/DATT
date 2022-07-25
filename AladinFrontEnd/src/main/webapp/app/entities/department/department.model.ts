import { IUser } from 'app/entities/user/user.model';

export interface  IDepartment {
  id?: number;
  departmentName?: string;
  user?: IUser;
}

export class Department implements IDepartment {
  constructor(public id?: number, public departmentName?: string, public user?: IUser) {}
}

export function getDepartmentIdentifier(department: IDepartment): number | undefined {
  return department.id;
}

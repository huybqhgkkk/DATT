import * as dayjs from 'dayjs';

export interface IContact {
  id?: number;
  name?: string;
  email?: string;
  message?: string | null;
  phone?: string;
  datecontact?: dayjs.Dayjs | null;
  jobtitle?: string | null;
  company?: string | null;
  interest?: string | null;
}

export class Contact implements IContact {
  constructor(
    public id?: number,
    public name?: string,
    public email?: string,
    public message?: string | null,
    public phone?: string,
    public datecontact?: dayjs.Dayjs | null,
    public jobtitle?: string | null,
    public company?: string | null,
    public interest?: string | null
  ) {}
}

export function getContactIdentifier(contact: IContact): number | undefined {
  return contact.id;
}

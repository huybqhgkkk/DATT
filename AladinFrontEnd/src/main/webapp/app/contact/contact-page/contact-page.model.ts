export interface IContact {
  id?: number;
  name?: string;
  email?: string;
  message?: string;
  phone?: string;
  interest?: string;
  jobtitle?: string;
  company?: string;
}

export class Contact implements IContact {
  constructor(
    id?: String,
    name?: String,
    email?: String,
    message?: String,
    phone?: String,
    interest?: String,
    jobtitle?: String,
    company?: String
  ) {}
}

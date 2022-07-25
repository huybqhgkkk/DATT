import * as dayjs from 'dayjs';

export interface IEmailtemplate {
  id?: number;
  templatename?: string;
  subject?: string;
  hyperlink?: string | null;
  datetime?: dayjs.Dayjs | null;
  content?: string;
}

export class Emailtemplate implements IEmailtemplate {
  constructor(
    public id?: number,
    public templatename?: string,
    public subject?: string,
    public hyperlink?: string | null,
    public datetime?: dayjs.Dayjs | null,
    public content?: string
  ) {}
}

export function getEmailtemplateIdentifier(emailtemplate: IEmailtemplate): number | undefined {
  return emailtemplate.id;
}

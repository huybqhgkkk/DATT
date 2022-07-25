import * as dayjs from 'dayjs';

export interface IRecruitmen {
  id?: number;
  position?: string;
  description?: string;
  require?: string;
  benefit?: string;
  amount?: number;
  job?: string | null;
  location?: string | null;
  duration?: dayjs.Dayjs | null;
  level?: string | null;
}

export class Recruitmen implements IRecruitmen {
  constructor(
    public id?: number,
    public position?: string,
    public description?: string,
    public require?: string,
    public benefit?: string,
    public amount?: number,
    public job?: string | null,
    public location?: string | null,
    public duration?: dayjs.Dayjs | null,
    public level?: string | null
  ) {}
}

export function getRecruitmenIdentifier(recruitmen: IRecruitmen): number | undefined {
  return recruitmen.id;
}

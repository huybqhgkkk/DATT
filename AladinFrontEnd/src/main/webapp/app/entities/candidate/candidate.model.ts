import * as dayjs from 'dayjs';

export interface ICandidate {
  id?: number;
  phone?: string;
  email?: string;
  position?: string;
  birthday?: dayjs.Dayjs;
  location?: string;
  preference?: string | null;
  education?: string;
  experience?: string;
  target?: string | null;
  relationship?: string | null;
  dateRegister?: dayjs.Dayjs | null;
  fullname?: string | null;
  sex?: string | null;
  cvContentType?: string | null;
  cv?: string | null;
}

export class Candidate implements ICandidate {
  constructor(
    public id?: number,
    public phone?: string,
    public email?: string,
    public position?: string,
    public birthday?: dayjs.Dayjs,
    public location?: string,
    public preference?: string | null,
    public education?: string,
    public experience?: string,
    public target?: string | null,
    public relationship?: string | null,
    public dateRegister?: dayjs.Dayjs | null,
    public fullname?: string | null,
    public sex?: string | null,
    public cvContentType?: string | null,
    public cv?: string | null
  ) {}
}

export function getCandidateIdentifier(candidate: ICandidate): number | undefined {
  return candidate.id;
}

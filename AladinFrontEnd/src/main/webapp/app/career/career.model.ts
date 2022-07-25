import * as dayjs from 'dayjs';

export interface ICareer {
  id?: number;
  position?: string;
  description?: string;
  require?: string;
  benefit?: string;
  amount?: number;
  job?: string | null;
  location?: string | null;
  duration?: Date;
  level?: string | null;
}

export class Career implements ICareer {
  constructor(
    id?: number,
    position?: string,
    description?: string,
    require?: string,
    benefit?: string,
    amount?: number,
    job?: string | null,
    location?: string | null,
    duration?: dayjs.Dayjs | null,
    level?: string | null
  ) {}
}

export class Candidate {
  birthday: string = ''; //2021-08-10,
  cv: string = '';
  cvContentType: string = '';
  dateRegister: string = '';
  education: string = '';
  email: string = '';
  experience: string = '';
  fullname: string = '';
  id?: number;
  location: string = '';
  phone: string = '';
  position: string = '';
  preference: string = '';
  relationship: string = '';
  sex: string = '';
  target: string = '';

  constructor(
    birthday: string, //2021-08-10,
    cv: string,
    cvContentType: string,
    dateRegister: string,
    education: string,
    email: string,
    experience: string,
    fullname: string,
    id: number,
    location: string,
    phone: string,
    position: string,
    preference: string,
    relationship: string,
    sex: string,
    target: string
  ) {
    this.birthday = birthday;
    this.cv = cv;
    this.cvContentType = cvContentType;
    this.dateRegister = dateRegister;
    this.education = education;
    this.email = email;
    this.experience = experience;
    this.fullname = fullname;
    this.id = id;
    this.location = location;
    this.phone = phone;
    this.position = position;
    this.preference = preference;
    this.relationship = relationship;
    this.sex = sex;
    this.target = target;
  }
}

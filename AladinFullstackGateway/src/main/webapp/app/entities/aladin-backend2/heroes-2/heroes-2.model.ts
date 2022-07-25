import * as dayjs from 'dayjs';
import { IViettelSubscriber2 } from 'app/entities/aladin-backend2/viettel-subscriber-2/viettel-subscriber-2.model';

export interface IHeroes2 {
  id?: number;
  name?: string | null;
  create_time?: dayjs.Dayjs | null;
  viettelSubscriber2?: IViettelSubscriber2 | null;
}

export class Heroes2 implements IHeroes2 {
  constructor(
    public id?: number,
    public name?: string | null,
    public create_time?: dayjs.Dayjs | null,
    public viettelSubscriber2?: IViettelSubscriber2 | null
  ) {}
}

export function getHeroes2Identifier(heroes2: IHeroes2): number | undefined {
  return heroes2.id;
}

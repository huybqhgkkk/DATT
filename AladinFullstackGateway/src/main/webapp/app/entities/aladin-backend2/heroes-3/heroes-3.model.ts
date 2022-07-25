import * as dayjs from 'dayjs';
import { IViettelSubscriber2 } from 'app/entities/aladin-backend2/viettel-subscriber-2/viettel-subscriber-2.model';

export interface IHeroes3 {
  id?: number;
  name?: string | null;
  create_time?: dayjs.Dayjs | null;
  viettelSubscriber2?: IViettelSubscriber2 | null;
}

export class Heroes3 implements IHeroes3 {
  constructor(
    public id?: number,
    public name?: string | null,
    public create_time?: dayjs.Dayjs | null,
    public viettelSubscriber2?: IViettelSubscriber2 | null
  ) {}
}

export function getHeroes3Identifier(heroes3: IHeroes3): number | undefined {
  return heroes3.id;
}

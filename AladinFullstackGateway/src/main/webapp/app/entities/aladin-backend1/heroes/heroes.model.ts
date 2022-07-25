import * as dayjs from 'dayjs';
import { IViettelSubscriber } from 'app/entities/aladin-backend1/viettel-subscriber/viettel-subscriber.model';

export interface IHeroes {
  id?: number;
  name?: string | null;
  create_time?: dayjs.Dayjs | null;
  viettelSubscriber?: IViettelSubscriber | null;
}

export class Heroes implements IHeroes {
  constructor(
    public id?: number,
    public name?: string | null,
    public create_time?: dayjs.Dayjs | null,
    public viettelSubscriber?: IViettelSubscriber | null
  ) {}
}

export function getHeroesIdentifier(heroes: IHeroes): number | undefined {
  return heroes.id;
}

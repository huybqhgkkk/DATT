export interface IViettelSubscriber2 {
  id?: number;
  name?: string | null;
  msisdn?: string | null;
}

export class ViettelSubscriber2 implements IViettelSubscriber2 {
  constructor(public id?: number, public name?: string | null, public msisdn?: string | null) {}
}

export function getViettelSubscriber2Identifier(viettelSubscriber2: IViettelSubscriber2): number | undefined {
  return viettelSubscriber2.id;
}

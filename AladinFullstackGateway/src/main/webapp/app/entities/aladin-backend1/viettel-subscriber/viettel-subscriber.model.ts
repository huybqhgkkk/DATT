export interface IViettelSubscriber {
  id?: number;
  name?: string | null;
  msisdn?: string | null;
}

export class ViettelSubscriber implements IViettelSubscriber {
  constructor(public id?: number, public name?: string | null, public msisdn?: string | null) {}
}

export function getViettelSubscriberIdentifier(viettelSubscriber: IViettelSubscriber): number | undefined {
  return viettelSubscriber.id;
}

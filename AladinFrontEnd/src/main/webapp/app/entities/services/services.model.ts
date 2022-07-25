import { IProduct } from '../product/product.model';

export interface IServices {
  id?: number;
  type?: string | null;
  description?: string | null;
  reason?: string | null;
  products?: IProduct[] | null;
}

export class Services implements IServices {
  constructor(
    public id?: number,
    public type?: string | null,
    public description?: string | null,
    public reason?: string | null,
    products?: IProduct[] | null
  ) {}
}

export function getServicesIdentifier(services: IServices): number | undefined {
  return services.id;
}

import { IProduct } from '../product/product.model';

export interface IService {
  id?: number;
  title?: string;
  type?: string;
  description?: string;
  reason?: string;
  products?: IProduct[];
}

export class ServiceModel implements IService {
  constructor(id?: number, title?: string, type?: string, descripsion?: string, reason?: string, products?: IProduct[]) {
    const ok = '1';
  }
}

export function getServiceIdentifier(service: IService): number | undefined {
  return service.id;
}

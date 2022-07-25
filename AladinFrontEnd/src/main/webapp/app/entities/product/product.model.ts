import { IServices } from '../services/services.model';

export interface IProduct {
  id?: number;
  name?: string | null;
  content?: string | null;
  imageContentType?: string | null;
  image?: string | null;
  id_type?: number;
  description?: string | null;
  services?: IServices | null;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public name?: string | null,
    public content?: string | null,
    public imageContentType?: string | null,
    public image?: string | null,
    description?: string | null,
    public services?: IServices | null
  ) {}
}

export function getProductIdentifier(product: IProduct): number | undefined {
  return product.id;
}

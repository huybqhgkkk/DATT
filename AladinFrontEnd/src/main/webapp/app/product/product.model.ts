import { IServices } from '../entities/services/services.model';
import { IService } from '../service/service.model';

export interface IProduct {
  id?: number;
  type?: string;
  name?: string;
  content?: string;
  description?: string;
  image?: any;
  id_type?: number;
  services?: IService;
}

export class Product implements IProduct {
  constructor(
    id?: number,
    type?: string,
    name?: string,
    content?: string,
    image?: any,
    id_type?: number,
    services?: IService,
    description?: string
  ) {
    const ok = '1';
  }
}

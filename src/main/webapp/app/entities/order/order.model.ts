import { IItem } from 'app/entities/item/item.model';

export interface IOrder {
  id?: string;
  sellerName?: string;
  buyerName?: string;
  priceSettled?: number;
  status?: string;
  completed?: boolean;
  item?: IItem | null;
}

export class Order implements IOrder {
  constructor(
    public id?: string,
    public sellerName?: string,
    public buyerName?: string,
    public priceSettled?: number,
    public status?: string,
    public completed?: boolean,
    public item?: IItem | null
  ) {
    this.completed = this.completed ?? false;
  }
}

export function getOrderIdentifier(order: IOrder): string | undefined {
  return order.id;
}

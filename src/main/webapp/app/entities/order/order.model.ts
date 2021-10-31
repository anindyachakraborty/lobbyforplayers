import { IItem } from 'app/entities/item/item.model';

export interface IOrder {
  id?: number;
  sellerName?: string;
  buyerName?: string;
  priceSettled?: number;
  status?: string;
  completed?: boolean;
  item?: IItem | null;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
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

export function getOrderIdentifier(order: IOrder): number | undefined {
  return order.id;
}

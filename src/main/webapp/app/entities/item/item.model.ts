import { IOrder } from 'app/entities/order/order.model';
import { IBargain } from 'app/entities/bargain/bargain.model';
import { ITags } from 'app/entities/tags/tags.model';

export interface IItem {
  id?: number;
  description?: string;
  views?: number;
  sellerName?: string;
  sellerId?: string;
  listedFlag?: boolean;
  price?: number;
  picturesPath?: string | null;
  level?: string | null;
  fixedPrice?: boolean;
  gameName?: string;
  language?: string;
  order?: IOrder | null;
  bargains?: IBargain[] | null;
  tags?: ITags[] | null;
}

export class Item implements IItem {
  constructor(
    public id?: number,
    public description?: string,
    public views?: number,
    public sellerName?: string,
    public sellerId?: string,
    public listedFlag?: boolean,
    public price?: number,
    public picturesPath?: string | null,
    public level?: string | null,
    public fixedPrice?: boolean,
    public gameName?: string,
    public language?: string,
    public order?: IOrder | null,
    public bargains?: IBargain[] | null,
    public tags?: ITags[] | null
  ) {
    this.listedFlag = this.listedFlag ?? false;
    this.fixedPrice = this.fixedPrice ?? false;
  }
}

export function getItemIdentifier(item: IItem): number | undefined {
  return item.id;
}

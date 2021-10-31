import { IItem } from 'app/entities/item/item.model';

export interface IBargain {
  id?: number;
  bargainPrice?: number;
  itemId?: string;
  sellerApproved?: boolean;
  buyerApproved?: boolean;
  sellerId?: string;
  buyerId?: string;
  item?: IItem | null;
}

export class Bargain implements IBargain {
  constructor(
    public id?: number,
    public bargainPrice?: number,
    public itemId?: string,
    public sellerApproved?: boolean,
    public buyerApproved?: boolean,
    public sellerId?: string,
    public buyerId?: string,
    public item?: IItem | null
  ) {
    this.sellerApproved = this.sellerApproved ?? false;
    this.buyerApproved = this.buyerApproved ?? false;
  }
}

export function getBargainIdentifier(bargain: IBargain): number | undefined {
  return bargain.id;
}

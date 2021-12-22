export interface IPayments {
  id?: string;
  buyerUserId?: string;
  sellerUserId?: string;
  sellerRecieved?: boolean;
  buyerTransactionId?: string | null;
  sellerTransactionId?: string | null;
  orderId?: string;
  packageId?: string;
  amount?: number;
  game?: string | null;
  status?: string;
}

export class Payments implements IPayments {
  constructor(
    public id?: string,
    public buyerUserId?: string,
    public sellerUserId?: string,
    public sellerRecieved?: boolean,
    public buyerTransactionId?: string | null,
    public sellerTransactionId?: string | null,
    public orderId?: string,
    public packageId?: string,
    public amount?: number,
    public game?: string | null,
    public status?: string
  ) {
    this.sellerRecieved = this.sellerRecieved ?? false;
  }
}

export function getPaymentsIdentifier(payments: IPayments): string | undefined {
  return payments.id;
}

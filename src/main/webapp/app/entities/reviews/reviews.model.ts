export interface IReviews {
  id?: string;
  question?: string;
  rating?: number;
  username?: string;
  orderId?: string | null;
}

export class Reviews implements IReviews {
  constructor(
    public id?: string,
    public question?: string,
    public rating?: number,
    public username?: string,
    public orderId?: string | null
  ) {}
}

export function getReviewsIdentifier(reviews: IReviews): string | undefined {
  return reviews.id;
}

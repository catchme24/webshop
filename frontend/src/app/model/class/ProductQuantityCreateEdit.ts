

export class ProductQuantityCreateEdit {
  productId: string;
  quantity: number;

  constructor(productId: string, quantity: number) {
    this.productId = productId;
    this.quantity = quantity;
  }
}

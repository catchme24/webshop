import {Product} from "./Product";

export class ProductQuantity {
  productId: number;
  quantity: number;


  constructor(productId: number, quantity: number) {
    this.productId = productId;
    this.quantity = quantity;
  }
}

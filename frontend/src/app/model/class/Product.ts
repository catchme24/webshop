
export class Product {
  productId: number;
  productName: string;
  price: number;
  description: string;
  imageUrl: string;


  constructor(productId: number, productName: string, price: number, description: string, imageUrl: string) {
    this.productId = productId;
    this.productName = productName;
    this.price = price;
    this.description = description;
    this.imageUrl = imageUrl;
  }


}

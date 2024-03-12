
export class Product {
  productId: string;
  productName: string;
  price: number;
  description: string;
  imageUrl: string;


  constructor(productId: string, productName: string, price: number, description: string, imageUrl: string) {
    this.productId = productId;
    this.productName = productName;
    this.price = price;
    this.description = description;
    this.imageUrl = imageUrl;
  }


}

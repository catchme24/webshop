

export class Product {
  productId: number | null = null;
  productName: string;
  price: number;
  description: string;
  imageUrl: string;

  constructor(data: any) {
    this.productId = data?.productId;
    this.productName = data?.productName;
    this.description = data?.description;
    this.price = data?.price;
    this.imageUrl = data?.imageUrl;
  }

}


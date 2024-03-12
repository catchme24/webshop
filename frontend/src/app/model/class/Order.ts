import {Product} from "./Product";
import {DeliveryList} from "./DeliveryList";
import {ProductQuantityRead} from "./ProductQuantityRead";

export class Order {
    orderId: string;
    products: ProductQuantityRead[];
    dateGet: string;
    deliveryListReadDto: DeliveryList;


  constructor(orderId: string, products: ProductQuantityRead[], dateGet: string, deliveryListReadDto: DeliveryList) {
    this.orderId = orderId;
    this.products = products;
    this.dateGet = dateGet;
    this.deliveryListReadDto = deliveryListReadDto;
  }
}

import {Product} from "./Product";
import {DeliveryList} from "./DeliveryList";
import {ProductQuantity} from "./ProductQuantity";

export class Order {
    orderId: string;
    productsWithQuantity: ProductQuantity[];
    dateGet: string;
    deliveryList: DeliveryList;


    constructor(orderId: string, productsWithQuantity: ProductQuantity[], dateGet: string, deliveryList: DeliveryList) {
        this.orderId = orderId;
        this.productsWithQuantity = productsWithQuantity;
        this.dateGet = dateGet;
        this.deliveryList = deliveryList;
    }
}

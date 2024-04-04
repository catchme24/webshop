import {Product} from "./Product";
import {DeliveryList} from "./DeliveryList";
import {ProductQuantity} from "./ProductQuantity";

export class Order {
    orderId: string;
    productsWithQuantity: ProductQuantity[];
    products: Product[];
    dateGet: string;
    deliveryList: DeliveryList;


    constructor(orderId: string, productsWithQuantity: ProductQuantity[], products: Product[], dateGet: string, deliveryList: DeliveryList) {
        this.orderId = orderId;
        this.productsWithQuantity = productsWithQuantity;
        this.products = products;
        this.dateGet = dateGet;
        this.deliveryList = deliveryList;
    }
}

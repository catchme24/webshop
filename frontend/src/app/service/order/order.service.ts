import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {AuthService} from "../auth/auth.service";
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {ProductQuantityCreateEdit} from "../../model/class/ProductQuantityCreateEdit";
import {DeliveryList} from "../../model/class/DeliveryList";
import {Order} from "../../model/class/Order";


@Injectable({
  providedIn: 'root'
})
export class OrderService {
  //Даёт возможность подписаться на изменения этого сервиса
  subject = new Subject();
  private apiUrl: string = 'http://localhost:8080/api/orders';

  constructor(private http: HttpClient,
              private authService: AuthService) {}

  public addProductInOrder(orderId: string, productQuantity: ProductQuantityCreateEdit): Observable<HttpResponse<any>> {
    return this.http.post(this.apiUrl + `/${orderId}`,
        productQuantity,
        this.authService.getHttpOptionsWithAuth());
  }

  public changeCountOfProduct(orderId: string, productQuantity: ProductQuantityCreateEdit): Observable<HttpResponse<any>> {
    return this.http.put(this.apiUrl + `/${orderId}`,
        productQuantity,
        this.authService.getHttpOptionsWithAuth());
  }

  public deleteProductFromOrder(orderId: string, productId: string): Observable<HttpResponse<any>> {
    return this.http.delete(this.apiUrl + `/${orderId}` + `/product/${productId}`,
      this.authService.getHttpOptionsWithAuth());
  }

  public addDeliveryListToOrder(orderId: string, deliveryList: DeliveryList): Observable<HttpResponse<any>> {
    return this.http.post(`http://localhost:8080/orders/${orderId}/form-order`,
      deliveryList,
      this.authService.getHttpOptionsWithAuth());
  }

  public getOrdersOfCurrentUser(): Observable<HttpResponse<Order[]>> {
    return this.http.get<Order[]>(this.apiUrl,
        this.authService.getHttpOptionsWithAuth());
  }
}

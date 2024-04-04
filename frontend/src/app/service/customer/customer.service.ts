import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {AuthService} from "../auth/auth.service";
import {Observable, Subject} from "rxjs";
import {Order} from "../../model/class/Order";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  //Даёт возможность подписаться на изменения этого сервиса
  subject = new Subject();
  private apiUrl: string = 'http://localhost:8080/api/customers';

  constructor(private http: HttpClient,
              private authService: AuthService){
  }

  getOrdersByUserId(userId: string): Observable<HttpResponse<Order[]>> {
    return this.http.get<Order[]>(this.apiUrl + `/${userId}`,
      this.authService.getHttpOptionsWithAuth());
  }
}

import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {AuthService} from "../auth/auth.service";
import {Observable, Subject} from "rxjs";
import {Product} from "../../model/class/Product";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  subject = new Subject<void>();
  private apiUrl: string = 'http://localhost:8080/api/products';

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public getAll(): Observable<HttpResponse<Product[]>> {
    return this.http.get<Product[]>(this.apiUrl,
      this.authService.getHttpOptions());
  }


  public getAllByIds(productsId: number[]): Observable<HttpResponse<Product[]>> {
    return this.http.get<Product[]>(this.apiUrl,
        this.authService.getHttpOptions());
  }

  public create(product: Product): Observable<HttpResponse<Product>> {
    return this.http.post<Product>(this.apiUrl,
      product,
      this.authService.getHttpOptionsWithAuth());
  }

  public delete(productId: string): Observable<HttpResponse<any>> {
    return this.http.delete(this.apiUrl + `/${productId}`,
      this.authService.getHttpOptionsWithAuth());
  }

}

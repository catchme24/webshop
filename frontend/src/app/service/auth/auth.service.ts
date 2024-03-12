import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {BehaviorSubject} from 'rxjs';
import {Authentication} from "../../model/type/Authentication";
import {Customer} from "../../model/class/Customer";
import {Login} from "../../model/class/Login";
import {MatDialog} from "@angular/material/dialog";
import {LoginInfo} from "../../model/class/LoginInfo";
import {Options} from "../../model/type/Options";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  //Даёт возможность подписаться на изменения этого сервиса
  subject = new BehaviorSubject<Authentication>({
    isAdmin: false,
    isAuthenticated: false
  });
  private LOCAL_STORAGE_USER_ID_NAME = 'userId';
  private LOCAL_STORAGE_TOKEN_NAME = 'token';
  private LOCAL_STORAGE_ROLE_NAME = 'role';
  private LOCAL_STORAGE_CURRENT_ORDER_ID = 'currentOrderId';
  private LOCAL_STORAGE_USER_ADDRESS = 'address';
  private apiUrl: string = 'http://localhost:8080/auth';

  constructor(private http: HttpClient,
              public dialog: MatDialog) {
    this.initAuthState();
  }


  private initAuthState(): void {
    const token = localStorage.getItem(this.LOCAL_STORAGE_TOKEN_NAME);
    const role = localStorage.getItem(this.LOCAL_STORAGE_ROLE_NAME);
    this.subject.next({
      isAuthenticated: !!token,
      isAdmin: role === "ADMIN"
    });
  }

  getAuthentication(): Authentication {
    return this.subject.getValue();
  }

  logout(): void {
    this.subject.next({
      isAdmin: false,
      isAuthenticated: false
    });
    localStorage.removeItem(this.LOCAL_STORAGE_TOKEN_NAME);
    localStorage.removeItem(this.LOCAL_STORAGE_USER_ID_NAME);
    localStorage.removeItem(this.LOCAL_STORAGE_ROLE_NAME);
    localStorage.removeItem(this.LOCAL_STORAGE_CURRENT_ORDER_ID);
    localStorage.removeItem(this.LOCAL_STORAGE_USER_ADDRESS);
  }

  getRole(): string {
    return localStorage.getItem(this.LOCAL_STORAGE_ROLE_NAME) || '';
  }
  getToken(): string {
    return localStorage.getItem(this.LOCAL_STORAGE_TOKEN_NAME) || '';
  }

  getUserId(): string {
    return localStorage.getItem(this.LOCAL_STORAGE_USER_ID_NAME) || '';
  }

  getCurrentOrderId(): string {
    return localStorage.getItem(this.LOCAL_STORAGE_CURRENT_ORDER_ID) || '';
  }

  getAddress() {
    return localStorage.getItem(this.LOCAL_STORAGE_USER_ADDRESS) || '';
  }

  setCurrentOrderId(currentOrderId: string): void {
    localStorage.setItem(this.LOCAL_STORAGE_CURRENT_ORDER_ID, currentOrderId);
  }

  loginCustomer(login: Login): void {
    this.http.post<LoginInfo>(this.apiUrl + '/login',
      JSON.stringify(login),
      this.getHttpOptions()).subscribe((responce: HttpResponse<LoginInfo>) => {
        if (responce.status == 200) {
          localStorage.setItem(this.LOCAL_STORAGE_USER_ID_NAME, responce.body?.userId || '');
          localStorage.setItem(this.LOCAL_STORAGE_TOKEN_NAME, responce.body?.token || '');
          localStorage.setItem(this.LOCAL_STORAGE_ROLE_NAME, responce.body?.role || '');
          localStorage.setItem(this.LOCAL_STORAGE_CURRENT_ORDER_ID, responce.body?.currentOrderId || '');
          localStorage.setItem(this.LOCAL_STORAGE_USER_ADDRESS, responce.body?.address || '');
          this.subject.next({
            isAuthenticated: !!this.getToken(),
            isAdmin: this.getRole() === "ADMIN"
          })
          this.dialog.closeAll();
        }
      },
      (error: any) => {
        alert(error.errorMessage);
      });
  }

  public registrCustomer(customer: Customer) {
    this.http.post(this.apiUrl + '/registration',
      JSON.stringify(customer),
      this.getHttpOptions()).subscribe((responce: HttpResponse<any>) => {
        if (responce.status == 201) {
          alert("Вы успешно зарегистрировались! Поздравляем!!! РЫБНОГО СЧАСТЬЯ!");
          this.dialog.closeAll();
        }
      },
      (error: any) => {
        alert("Пожалуйста, введите все необходимые поля для регистрации")
      });
  }

  getHttpOptions(): Options {
    const httpOptions: Options = {
      responseType: 'json',
      observe: "response",
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
    };
    return httpOptions;
  }

  getHttpOptionsWithAuth(): Options {
    const token = this.getToken() || '';
    const httpOptions: Options = {
      responseType: 'json',
      observe: 'response',
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }),
    };
    return httpOptions;
  }
}

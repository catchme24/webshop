import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {Router, RouterLink, RouterOutlet} from '@angular/router';
import {AuthService} from "./service/auth/auth.service";

import {
  MatDialog,
  MatDialogRef,
  MatDialogActions,
  MatDialogClose,
  MatDialogTitle,
  MatDialogContent,
} from '@angular/material/dialog';
import {LoginComponent} from "./component/login/login.component";
import {RegistrationComponent} from "./component/registration/registration.component";
import {Authentication} from "./model/type/Authentication";
import {Subscription} from "rxjs";
import {HttpResponse} from "@angular/common/http";
import {Order} from "./model/class/Order";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  //Есть у любого компонента, у которого в html логика отображения зависит от аутентификации/роли
  authentication: Authentication = {
    isAuthenticated: false,
    isAdmin: false
  };
  //Поле-подписка, хранится, чтобы перед его удалением отписаться
  private authServiceSub!: Subscription;
  //Для контроля отрисовок компоонента
  private counter = 0;

  constructor(private authService: AuthService,
              public dialog: MatDialog,
              private router: Router) {}

  ngOnInit(): void {
    this.authentication = this.authService.getAuthentication();
    //Подписка на изменения состояния аутентификации, есть везде где используется аутентификация
    this.authServiceSub = this.authService.subject.asObservable().subscribe((authentication) => {
      this.authentication = authentication;
    });
  }

  ngOnDestroy(): void {
    console.log("Компонент " + this.constructor.name + ' удаляется, он обновлялся: ' + this.counter + ' раз!');
    /*
      Отписка при удалении нужна, т.к. без неё ангуляр создает новый компонент на каждый переход
      по маршруту. Может получиться ситуация, что при заходе в корзину 20 раз, ангуляр создаст 20 компнонет и
      каждому будет обновлять состояние, хотя для рендера картинки использует один последний => при удалении
      компонента надо удалить все внешние зависимости (подписку), чтобы ангуляр удалил его с концами
    */
    this.authServiceSub.unsubscribe();
  }
  updatePage() {
  }

  openDialog() {
    this.dialog.open(LoginComponent);
  }

  openDialogSignUp() {
    this.dialog.open(RegistrationComponent);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/home']);
  }

}

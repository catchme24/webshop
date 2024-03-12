import {Component, OnInit} from '@angular/core';
import {OrderService} from "../../service/order/order.service";
import {AuthService} from "../../service/auth/auth.service";
import {Order} from "../../model/class/Order";
import {CustomerService} from "../../service/customer/customer.service";
import {Authentication} from "../../model/type/Authentication";
import {HttpResponse} from "@angular/common/http";
import {Subscription} from "rxjs";
import {DeliveryList} from "../../model/class/DeliveryList";
import {FormBuilder, FormGroup} from "@angular/forms";
import {MatSelectionListChange} from "@angular/material/list";

@Component({
  selector: 'app-basket-page',
  templateUrl: './basket-page.component.html',
  styleUrl: './basket-page.component.css'
})
export class BasketPageComponent implements OnInit {
  //Есть у любого компонента, у которого в html логика отображения зависит от аутентификации/роли
  authentication: Authentication = {
    isAuthenticated: false,
    isAdmin: true
  };
  totalCost: number = 0;
  totalMass: number = 0;
  payMethods = [{ name: 'Карта',
                    isActive: true
                  },
                  { name: 'Наличные',
                    isActive: false
                  }];
  contactForm!: FormGroup;
  //Поле-подписка, хранится, чтобы перед его удалением отписаться
  private orderServiceSub!: Subscription;
  private authServiceSub!: Subscription;
  //Для контроля отрисовок компоонента
  private counter = 0;

  currentOrder!: Order | undefined;
  prevOrders!: Order[];

  constructor(
      private customerService: CustomerService,
      private orderService: OrderService,
      public authService: AuthService,
      private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.contactForm = this.formBuilder.group({
        payMethod: [null]
    });
    this.authentication = this.authService.getAuthentication();
    //Подписка на изменения состояния аутентификации, есть везде где используется аутентификация
    this.authServiceSub = this.authService.subject.asObservable().subscribe((authentication) => {
      this.authentication = authentication;
    });
    //Подписка на изменения состояния заказов
    this.orderServiceSub = this.orderService.subject.asObservable().subscribe(() => {
      this.updatePage();
    });
    this.updatePage();
  }

  ngOnDestroy(): void {
    console.log("Компонент " + this.constructor.name + ' удаляется, он обновлялся: ' + this.counter + ' раз!');
    /*
      Отписка при удалении нужна, т.к. без неё ангуляр создает новый компонент на каждый переход
      по маршруту. Может получиться ситуация, что при заходе в корзину 20 раз, ангуляр создаст 20 компнонет и
      каждому будет обновлять состояние, хотя для рендера картинки использует один последний => при удалении
      компонента надо удалить все внешние зависимости (подписку), чтобы ангуляр удалил его с концами
    */
    this.orderServiceSub.unsubscribe();
    this.authServiceSub.unsubscribe();
  }

  updatePage() {
    this.counter++;
    console.log("Компонент " + this.constructor.name + ' обновился: ' + this.counter + ' раз!');

    const userId = this.authService.getUserId();
    this.customerService.getOrdersByUserId(userId).subscribe((response: HttpResponse<Order[]>) => {
      console.log(response.body);
      const orders = response.body;
      if (orders != null) {
        this.currentOrder = orders.find((order: Order) => order.deliveryListReadDto == null);
        this.authService.setCurrentOrderId(this.currentOrder?.orderId || '');
        console.log(this.currentOrder?.orderId);
          // @ts-ignore
        this.totalCost = this.currentOrder?.products
              .reduce((prev, current) =>
                  prev + current.quantity * current.product.price, 0);
        // @ts-ignore
          this.totalMass = this.currentOrder?.products
            .reduce((prev, current) =>
                prev + current.quantity, 0);
        this.prevOrders = orders.filter(order => order.deliveryListReadDto != null);
      }
    },(error: any) => {
      console.log(error);
    });
  }

  changeCountOfProduct(orderId: string, productId: string, quantity: number) {
    if (quantity != 0) {
      this.orderService.changeCountOfProduct(orderId, {productId, quantity})
        .subscribe((responce) => {
          this.orderService.subject.next({});
        }, (error: any) => {
          console.log(error);
        });
    } else {
      this.deleteProductFromOrder(orderId, productId);
    }
  }

  deleteProductFromOrder(orderId: string, productId: string) {
    this.orderService.deleteProductFromOrder(orderId, productId)
      .subscribe((responce) => {
        this.orderService.subject.next({});
      }, (error: any) => {
        console.log(error);
      });
  }

  formOrder() {
    const delivertList = new DeliveryList(
      '',
      this.payMethods[0].isActive == true ? this.payMethods[0].name : this.payMethods[1].name,
      '',
      ''
    );
    this.orderService.addDeliveryListToOrder(this.currentOrder?.orderId || '', delivertList)
      .subscribe((responce) => {
        this.orderService.subject.next({});
      },
        () => {
        alert("Упс! С оформлением заказа что-то пошло не так :(")
      });
  }

  some($event: MatSelectionListChange) {
    if (this.payMethods[0].isActive) {
      this.payMethods[0].isActive = false;
      this.payMethods[1].isActive = true;
    } else {
      this.payMethods[0].isActive = true;
      this.payMethods[1].isActive = false;
    }
  }

}

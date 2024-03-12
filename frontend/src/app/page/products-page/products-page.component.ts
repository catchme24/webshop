import {Component, OnInit} from '@angular/core';
import {HttpResponse} from "@angular/common/http";
import {MatDialog} from "@angular/material/dialog";
import {AuthService} from "../../service/auth/auth.service";
import {ProductService} from "../../service/product/product.service";
import {Product} from "../../model/class/Product";
import {LoginComponent} from "../../component/login/login.component";
import {OrderService} from "../../service/order/order.service";
import {ProductQuantityCreateEdit} from "../../model/class/ProductQuantityCreateEdit";
import {Authentication} from "../../model/type/Authentication";
import {Subscription} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";
import {AddProductComponent} from "../../component/add-product/add-product.component";

@Component({
  selector: 'app-products-page',
  templateUrl: './products-page.component.html',
  styleUrl: './products-page.component.css'
})
export class ProductsPageComponent implements OnInit {
  //Есть у любого компонента, у которого в html логика отображения зависит от аутентификации/роли
  authentication: Authentication = {
    isAuthenticated: false,
    isAdmin: true
  }
  //Поле-подписка, хранится, чтобы перед его удалением отписаться
  private productServiceSub!: Subscription;
  private authServiceSub!: Subscription;
  private currentOrderId!: string;
  //Для контроля отрисовок компоонента
  private counter = 0;

  products!: Product[];

  constructor(private authService: AuthService,
              private productService: ProductService,
              private orderService: OrderService,
              private matSnackBar: MatSnackBar,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.authentication = this.authService.getAuthentication();
    //Подписка на изменения состояния аутентификации, есть везде где используется аутентификация
    this.authServiceSub = this.authService.subject.asObservable().subscribe((authentication) => {
      this.authentication = authentication;
    });
    //Подписка на изменения состояния продуктов
    this.productServiceSub = this.productService.subject.asObservable().subscribe(() => {
      this.updatePage();
    });
    this.updatePage();
  }

  ngOnDestroy(): void {
    console.log("Компонент " + this.constructor.name + ' удаляется, он обновлялся: ' + this.counter + ' раз!');
    this.authServiceSub.unsubscribe();
    this.productServiceSub.unsubscribe();
  }

  updatePage() {
    this.counter++;
    console.log("Компонент " + this.constructor.name + ' обновился: ' + this.counter + ' раз!');

    this.productService.getAll().subscribe((responce) => {
        if (responce.body != null) {
          this.products = responce.body;
        }
      }
    );
  }

  openDialogLogin() {
    console.log("HERE")
    this.dialog.open(LoginComponent);
  }

  openDialogAddProduct() {
    console.log("HERE")
    this.dialog.open(AddProductComponent);
  }

  addProductToOrder(product: Product) {
    const productQuantity = new ProductQuantityCreateEdit(product.productId, 1);
    console.log(this.authService.getCurrentOrderId());
    this.orderService.addProductInOrder(this.authService.getCurrentOrderId(), productQuantity)
      .subscribe((responce) => {
          if(responce.status == 200) {
            this.matSnackBar.open("Вы успешно добавили продукт в корзину!", '', {
              duration: 2000,
              horizontalPosition: "right",
              verticalPosition: "bottom"
            });
            this.orderService.subject.next(this.currentOrderId);
          }
      }, (error) => {
          alert("У вас уже есть этот продукт в корзине! Вы можете менять его кол-во из корзины!")
      });
  }

  deleteProduct(productId: string) {
    this.productService.delete(productId).subscribe(
      (responce) => {
      this.productService.subject.next();
    },
      () => {
      alert("Данный продукт нельзя удалить, т.к. он используется в текущих заказах!")
    })
  }
}


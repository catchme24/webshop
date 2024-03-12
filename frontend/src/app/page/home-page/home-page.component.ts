import {Component} from '@angular/core';
import {CustomerService} from "../../service/customer/customer.service";
import {OrderService} from "../../service/order/order.service";
import {AuthService} from "../../service/auth/auth.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent {
  topImage: string = "https://deeper.fish/wp-content/uploads/2021/10/rybalka-na-soma-s-eholotom-0.jpg";
  private counter = 0;
  private ordersSub!: Subscription;

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.ordersSub = this.orderService.subject.asObservable().subscribe(() => {
      this.updatePage();
    });
    this.updatePage();
  }

  ngOnDestroy(): void {
    console.log("Компонент " + this.constructor.name + ' удаляется, он обновлялся: ' + this.counter + ' раз!');
    this.ordersSub.unsubscribe();
  }

  updatePage() {
    this.counter++;
    console.log("Компонент " + this.constructor.name + ' обновился: ' + this.counter + ' раз!');
  }
}

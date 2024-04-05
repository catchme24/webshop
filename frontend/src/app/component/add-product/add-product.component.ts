import {Component} from '@angular/core';
import {AddProductForm} from "../../model/type/AddProductForm";
import {ProductService} from "../../service/product/product.service";
import {Product} from "../../model/class/Product";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'add-product',
  templateUrl: 'add-product.component.html',
  styleUrls: ['add-product.component.css'],
})
export class AddProductComponent {
  addProductForm: AddProductForm = {
    productName: '',
    price: 0,
    description: '',
    imageUrl: ''
  };

  constructor(
    private productService: ProductService,
    private matSnackBar: MatSnackBar) {
  }

  ngOnInit() {

  }

  addProduct() {
    const product = new Product(
        this.addProductForm
    );

    this.productService.create(product).subscribe(
      (responce) => {
        this.productService.subject.next();
        this.matSnackBar.open("Вы успешно создали продукт!", '', {
          duration: 2000,
          horizontalPosition: "center",
          verticalPosition: "top"
        })
      },
      (error) => {
        alert('Для создания заполните все необходимые поля!')
      });
  }
}

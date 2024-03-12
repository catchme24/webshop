import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {HttpClient} from '@angular/common/http';
import {AuthService} from "../../service/auth/auth.service";
import {RegistrationForm} from "../../model/type/RegistrationForm";
import {Customer} from "../../model/class/Customer";

@Component({
  selector: 'registration',
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css'
})
export class RegistrationComponent {
  registrationForm: RegistrationForm = {
    username: '',
    password: '',
    firstName: '',
    lastName: '',
    phoneNumber: '',
    city: '',
    street: '',
    house: '',
    apartment: ''
  };

  uri = 'http://localhost:8080/auth';

  constructor(
    private authService: AuthService,
    public dialog: MatDialog,
    private http: HttpClient,
    public dialogRef: MatDialogRef<RegistrationComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  registr() {
    this.authService.registrCustomer(new Customer(
      this.registrationForm.username,
      this.registrationForm.password,
      this.registrationForm.firstName,
      this.registrationForm.lastName,
      this.registrationForm.phoneNumber,
      this.registrationForm.city,
      this.registrationForm.street,
      Number(this.registrationForm.house),
      Number(this.registrationForm.apartment)
    ));
    //ОБЪЕКТ ЗАГЛУШКА К РАНДОМНОМУ API И ПРОВЕРКА POST МЕТОДА
    //
    //
    //
    // let phone = {
    //   "name": "Apple MacBook Pro 16",
    //   "data": {
    //     "year": 2019,
    //     "price": 1849.99,
    //     "CPU model": "Intel Core i9",
    //     "Hard disk size": "1 TB"
    //   }
    // };
    // this.http.post<Some>('https://api.restful-api.dev/objects', JSON.stringify(phone), httpOptions).subscribe(
    //     (res: HttpResponse<Some>) => {
    //       console.log(res.status);
    //       console.log(res.body);
    //       // console.log(res.body);
    //       // console.log(res.status);
    //       // if (res.status == 200) {
    //       //   alert("Успешная регистрация!");
    //       //   this.dialogRef.close();
    //       // }
    //     }
  }

  ngOnInit() {

  }
}

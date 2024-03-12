import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {HttpClient} from '@angular/common/http';
import {AuthService} from "../../service/auth/auth.service";
import {LoginForm} from "../../model/type/LoginForm";
import {Login} from "../../model/class/Login";

@Component({
  selector: 'login',
  templateUrl: 'login.component.html',
  styleUrls: ['login.component.css'],
})
export class LoginComponent {
  loginForm: LoginForm = {
    username: '',
    password: '',
  };

  uri = 'http://localhost:8080/auth';

  constructor(
    private authService: AuthService,
    public dialog: MatDialog,
    private http: HttpClient,
    public dialogRef: MatDialogRef<LoginComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
  }

  public login() {
    this.authService.loginCustomer(new Login(
      this.loginForm.username,
      this.loginForm.password
    ));
  }

  ngOnInit() {

  }
}

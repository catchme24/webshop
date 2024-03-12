import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule, provideHttpClient, withFetch } from '@angular/common/http';

// import { AppRoutingModule } from './app-routing.module';
import { AppComponent} from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import {MatDialogContent} from "@angular/material/dialog";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatSnackBarModule } from "@angular/material/snack-bar";
import {HomePageComponent} from "./page/home-page/home-page.component";
import {ProductsPageComponent} from "./page/products-page/products-page.component";
import {BasketPageComponent} from "./page/basket-page/basket-page.component";
import {LoginComponent} from "./component/login/login.component";
import {RegistrationComponent} from "./component/registration/registration.component";
import {ResponceTypeInterceptor} from "./interceptor/ResponceTypeInterceptor";
import {AuthGuard} from "./guard/auth.guard";
import { AddProductComponent } from './component/add-product/add-product.component';
import {MatListModule} from "@angular/material/list";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomePageComponent,
    RegistrationComponent,
    ProductsPageComponent,
    BasketPageComponent,
    AddProductComponent,
  ],
  imports: [
    BrowserModule,
    MatIconModule,
    MatSnackBarModule,
    FormsModule,
    ReactiveFormsModule,
    MatCardModule,
    HttpClientModule,
    BrowserAnimationsModule,

    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatIconModule,

    MatChipsModule,

    RouterModule.forRoot([
      {path: 'home', component: HomePageComponent},
      {path: 'products', component: ProductsPageComponent},
      {path: 'basket', component: BasketPageComponent, canActivate: [AuthGuard]},
      {path: '', redirectTo: '/home', pathMatch: 'full'},
      {path: '**', redirectTo: '/home'}
    ]),
    MatDialogContent,
    MatListModule,

  ],

  providers: [
    AuthGuard,
    ResponceTypeInterceptor
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

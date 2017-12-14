import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { routing } from './app.routing';

import { OrderService } from './services/order.service';
import { CheckoutService } from './services/checkout.service';
import { CartService } from './services/cart.service';
import { BookService } from './services/book.service';
import { ShippingService} from './services/shipping.service';
import { LoginService } from './services/login.service';
import { UserService } from './services/user.service';
import { PaymentService } from './services/payment.service';
import { HttpModule } from '@angular/http';

import {MatButtonModule, MatCheckboxModule} from '@angular/material';
import 'hammerjs';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { MyAccountComponent } from './components/my-account/my-account.component';
import {MatTabsModule} from '@angular/material/tabs';
import { FormsModule } from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { RegisterUserComponent } from './components/register-user/register-user.component';
import { ForgetPasswordComponent } from './components/forget-password/forget-password.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatCardModule} from '@angular/material/card';
import { MyProfileComponent } from './components/my-profile/my-profile.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { BookListComponent } from './components/book-list/book-list.component';
import {MatMenuModule} from '@angular/material/menu';

import { DataTablesModule } from 'angular-datatables';
import { BookDetailComponent } from './components/book-detail/book-detail.component';
import { ShoppingCartComponent } from './components/shopping-cart/shopping-cart.component';
import { OrderComponent } from './components/order/order.component';
import { OrderSummaryComponent } from './components/order-summary/order-summary.component';



@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavBarComponent,
    MyAccountComponent,
    RegisterUserComponent,
    ForgetPasswordComponent,
    MyProfileComponent,
    BookListComponent,
    BookDetailComponent,
    ShoppingCartComponent,
    OrderComponent,
    OrderSummaryComponent,
  ],
  imports: [
  	MatButtonModule,
  	MatCheckboxModule,
    BrowserModule,
    routing,
    HttpModule,
    MatTabsModule,
    FormsModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatCardModule,
    MatProgressSpinnerModule,
    DataTablesModule,
    MatMenuModule
  ],
  providers: [
    LoginService,
    UserService,
    PaymentService,
    ShippingService,
    BookService,
    CartService,
    OrderService,
    CheckoutService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

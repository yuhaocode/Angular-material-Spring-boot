import { Component, OnInit } from '@angular/core';
import {AppConst} from '../../constants/app-const';
import { User } from '../../models/user';
import { UserService } from '../../services/user.service';
import { LoginService} from '../../services/login.service';
import { Router } from '@angular/router';
import {PaymentService} from '../../services/payment.service';
import { UserPayment } from '../../models/user-payment';
import { UserBilling } from '../../models/user-billing';
import {UserShipping} from '../../models/user-shipping';
import {ShippingService } from '../../services/shipping.service';
import { Order } from '../../models/order';
import { OrderService } from '../../services/order.service';


@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {

	private serverPath = AppConst.serverPath;
	dataFetched = false;
	private loginError : boolean;
	private credential = {'username':'', 'password':''};

  private selectedProfileTab : number = 0;
  private selectedBillingTab : number = 0;

  private userPayment : UserPayment = new UserPayment();
  private userBilling : UserBilling = new UserBilling();
  private userPaymentList : UserPayment[] = [];

  private cancelButtonDisabled : boolean;
	private loggedIn : boolean;
	private user:User = new User();
	private updateSuccess : boolean;
	private newPassword: string;
	private incorrectPassword: boolean;
  private currentPassword : string;
  private buttondisable = false;

  private defaultPaymentSet : boolean;
  private defaultUserPaymentId : number;
  private stateList :string[] = [];

  private userShipping : UserShipping = new UserShipping();
  private userShippingList : UserShipping[] = [];
  private defaultUserShippingId : number = 0;
  private defaultShippingSet : boolean;
  private selectedShippingTab: number = 0;

  private orderList : Order[] = [];
  private order : Order = new Order();
  private displayOrderDetail:boolean;

  constructor(
  	private loginService : LoginService,
  	private userService : UserService,
  	private router : Router,
    private orderService : OrderService,
    private paymentService : PaymentService,
    private shippingService : ShippingService) { }


  isDisabled(order : Order){
    if(order.orderStatus == "cancel"){
      this.cancelButtonDisabled = true;
    }
    else{
      this.cancelButtonDisabled = false;
    }

  }

  selectedShippingChange(val : number){
    this.selectedShippingTab = val;
  }

  selectedBillingChange(val : number){
    this.selectedBillingTab = val;
  }

  onRemovePayment(id : number){
     this.paymentService.removePayment(id).subscribe(
      res => {
        this.getCurrentUser();
        this.selectedBillingTab = 0;
      },
      err => {
        console.log(err.text());
      }
      );
  }

  onUpdateUserInfo(){
  	this.userService.updateUserInfo(this.user, this.newPassword,this.currentPassword).subscribe(
  		res => {
  			console.log(res.text());
  			this.updateSuccess = true;
  		},
  		err => {
  			console.log(err.text());
  			let errorMessage = err.text();
  			if(errorMessage === "Incorrect current password!") this.incorrectPassword  = true;
  		}
  		);
  }

  getCurrentUser(){
    this.userService.getCurrentUser().subscribe(
      res =>{
        this.user = res.json();
        this.userPaymentList = this.user.userPaymentList;
        this.userShippingList = this.user.userShippingList;

        for(let index in this.userShippingList){
          if(this.userShippingList[index].userShippingDefault){
            this.defaultUserShippingId = this.userShippingList[index].id;
          }
        }

        for(let index in this.userPaymentList){
          if(this.userPaymentList[index].defaultPayment ){
            this.defaultUserPaymentId = this.userPaymentList[index].id;
          }
        }
        this.dataFetched = true;
      },
      err =>{
        console.log(err);
      }
      );
  }

  onNewPayment(){
    this.paymentService.newPayment(this.userPayment).subscribe(
      res => {
        this.getCurrentUser();
        this.selectedBillingTab = 0;
        this.userPayment = new UserPayment();
      },
      err => {
        console.log(err.text());
      }
      );
  }

  setDefaultPayment() {
    this.defaultPaymentSet = false;
     this.paymentService.setDefaultPayment(this.defaultUserPaymentId).subscribe(
      res => {
        this.getCurrentUser();
        this.defaultPaymentSet = true;
      },
      err => {
        console.log(err.text());
      }
      );
  }



  onUpdatePayment(payment : UserPayment){
    this.userPayment = payment;
    this.userBilling = payment.userBilling;
    this.selectedBillingTab = 1;
  }

  onNewShipping() {
    this.shippingService.newShipping(this.userShipping).subscribe(
      res => {
        this.getCurrentUser();
        this.selectedShippingTab=0;
        this.userShipping = new UserShipping();
      },
      error => {
        console.log(error.text());
      }
    );
  }

  onUpdateShipping(shipping: UserShipping) {
    this.userShipping = shipping;
    this.selectedShippingTab = 1;
  }

  onRemoveShipping(id: number) {
    this.shippingService.removeShipping(id).subscribe(
      res => {
        this.getCurrentUser();
      },
      error => {
        console.log(error.text());
      }
    );
  }

  setDefaultShipping() {
    this.defaultShippingSet = false;
    this.shippingService.setDefaultShipping(this.defaultUserShippingId).subscribe(
      res => {
        this.getCurrentUser();
        this.defaultShippingSet = true;
      },
      error => {
        console.log(error.text());
      }
    );
  }

  onDisplayOrder(order : Order){
    console.log(order);
    this.order = order;
    this.displayOrderDetail = true;
  }

  RemoveOrder(order : Order){
    this.orderService.changeStatus(order).subscribe(
        res => {
          location.reload();
        },
        error => {
          console.log(error);
        }
      );
    
  }

  ngOnInit() {
  	this.loginService.checkSession().subscribe(
  		res =>{
  			this.loggedIn = true;
  		},
  		error => {
  			this.loggedIn = false;
  			console.log("inactive session");
  			this.router.navigate(['/myAccount']);
  		}
  		);

    this.getCurrentUser();

    for(let s in AppConst.usStates){
      this.stateList.push(s);
    }
    this.orderService.getOrderList().subscribe(
      res => {
        this.orderList = res.json();
      },
      err => {
        console.log(err);
      }
    );

    this.userBilling.userBillingState = "";
    this.userPayment.type = "";
    this.userPayment.expiryMonth = "";
    this.userPayment.expiryYear = "";
    this.userPayment.userBilling = this.userBilling;
    this.defaultPaymentSet = false;

    this.userShipping.userShippingState = "";
    this.defaultShippingSet = false;
  }

  check(s1 : string, s2 : string){
    console.log(s1);
    console.log(s2);
    if(s1 == s2){
      this.buttondisable = false;
      console.log(this.buttondisable);
    }
    else{
      this.buttondisable = true;
    }
    
  }
}

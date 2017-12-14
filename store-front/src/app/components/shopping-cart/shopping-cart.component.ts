import { Component, OnInit } from '@angular/core';
import {AppConst} from '../../constants/app-const';
import { Router } from '@angular/router';
import { ShoppingCart } from '../../models/shopping-cart';
import { CartService } from '../../services/cart.service';
import { CartItem } from '../../models/cart-item';
import { Book } from '../../models/book';
@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  serverPath = AppConst.serverPath;
  selectedBook : Book;
  cartItemList : CartItem[] = [];
  cartItemNumber : number;
  shoppingCart : ShoppingCart = new ShoppingCart();
  cartItemUpdated : boolean;
  emptyCart : boolean;
  notEnoughStock : boolean;

  constructor(
  	private router : Router,
  	private cartService : CartService
  	) { }

  onSelect(book : Book){
  	this.selectedBook = book;
  	this.router.navigate(['/bookDetail', this.selectedBook.id]);
  }

  onRemoveCartItem(cartItem : CartItem){
  	this.cartService.removeCartItem(cartItem.id).subscribe(
  		res => {
  			console.log(res.text());
  			this.getCartItemList();
  			this.getShoppingCart();
  		},
  		error => {
  			console.log(error.text);
  		}
  	);
  }

  onUpdateCartItem(cartItem : CartItem){
  	this.cartService.updateCartItem(cartItem.id,cartItem.qty).subscribe(
  		res => {
  			console.log(res.text());
  			this.cartItemUpdated = true;
  			this.getShoppingCart();
  		},
  		err => {
  			console.log(err.text());
  		}
  	);
  }

  getCartItemList(){
  	  	this.cartService.getCartItemList().subscribe(
  		res => {
  			this.cartItemList = res.json();
  			this.cartItemNumber = this.cartItemList.length;
  		},
  		err => {
  			console.log(err.text());
  		}
  	);
  }

    getShoppingCart(){
  	  	this.cartService.getShoppingCart().subscribe(
  		res => {
  			console.log(res.json());
  			this.shoppingCart = res.json();
		},
  		err => {
  			console.log(err.text());
  		}
  	);
  }

onCheckOut(){
	if(this.cartItemNumber == 0){
		this.emptyCart = true;
	}
	else{
		for(let item of this.cartItemList){
			if(item.qty > item.book.inStockNumber){
				console.log("not enough stock on same item");
				this.notEnoughStock = true;
				return;
			}
		}
		// this.router.navigate('[/order]');
	}
}

  ngOnInit() {

  	this.getShoppingCart();
  	this.getCartItemList();
  }

}

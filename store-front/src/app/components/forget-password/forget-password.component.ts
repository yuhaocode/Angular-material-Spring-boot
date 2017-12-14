import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Http } from '@angular/http';
import { Router } from '@angular/router'
import { AppConst } from '../../constants/app-const';

@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.css']
})
export class ForgetPasswordComponent implements OnInit {
  private forgetPasswordEmailSent : boolean;
  emailNotExists : boolean;
  emailSent : boolean;
  usernameExists: boolean;
  recoverEmail : string;
  dataLoading: boolean = false;

  private serverPath = AppConst.serverPath;


  constructor(private userService : UserService,
    private http : Http,
    private router : Router) { }

    onForgetPassword(){
  	this.forgetPasswordEmailSent = false;
  	this.emailNotExists = false;
    this.dataLoading = true;
  	this.userService.retrievePassword(this.recoverEmail).subscribe(
      
  		res => {
  			console.log(res);
  			this.emailSent = true;
        this.dataLoading = false;
  		},
  		error =>{
  			console.log(error.text());
  			let errorMessage = error.text()
  			if(errorMessage === "Email not found"){
  				this.emailNotExists = true;	
  			}
    			this.dataLoading = false;
  		}
      
  	);

  }

  ngOnInit() {
  }

}

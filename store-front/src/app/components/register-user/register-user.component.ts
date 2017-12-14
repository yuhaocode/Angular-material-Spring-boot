import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { UserService } from '../../services/user.service';
import { AppConst } from '../../constants/app-const';
import { Http } from '@angular/http';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent implements OnInit {
 private serverPath = AppConst.serverPath;
  public loginError:boolean = false;
  public loggedIn = false;
  public credential = {'username':'' , 'password':''};

  public firstName : string;
  public lastName : string;
  public password : string;
  public dataLoading : boolean =false;
  public emailSent : boolean = false;
  public usernameExists:boolean;
  public emailExists:boolean;
  public username : string;
  public email : string;
  public emailNotExists:boolean = false;
  public forgetPasswordEmailSent:boolean;
  private recoverEmail: string;

  constructor(private loginService: LoginService,
  			  private userService : UserService,
  			  private router : Router,
          private http : Http) { }

  onNewAccount(){
  	this.dataLoading = true;
  	this.usernameExists = false;
  	this.emailExists = false;
  	this.emailSent = false;

  	this.userService.newUser(this.username, this.email, this.firstName, this.lastName, this.password).subscribe(
  		res => {
  			console.log(res);
  			this.emailSent = true;
        this.dataLoading = false;
        this.router.navigate(['/myAccount']);
  		},
  		err => {
  			console.log(err.text());
  			let errMessage = err.text();
  			if(errMessage === "usernameExists"){
  				this.usernameExists = true;	
  			}
  			if(errMessage === "emailExists") this.emailExists = true;
  		}

      
  	);
    
  }
  ngOnInit() {
  }

}

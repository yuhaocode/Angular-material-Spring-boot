import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { UserService } from '../../services/user.service';
import { AppConst } from '../../constants/app-const';
import { Http } from '@angular/http';

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.css']
})
export class MyAccountComponent implements OnInit {

  private serverPath = AppConst.serverPath;
  public loginError:boolean = false;
  public loggedIn = false;
  public credential = {'username':'' , 'password':''};

  public dataLoading : boolean =false;
  public emailSent : boolean = false;
  private usernameExists:boolean;
  private emailExists:boolean;
  private username : string;
  private email : string;
  private emailNotExists:boolean = false;
  private forgetPasswordEmailSent:boolean;
  private recoverEmail: string;

  constructor(private loginService: LoginService,
  			  private userService : UserService,
  			  private router : Router,
          private http : Http) { }

  onLogin(){
    this.dataLoading = true;
  	this.loginService.sendCredential(this.credential.username, this.credential.password).subscribe(
  		res =>{
  			console.log(res);
  			localStorage.setItem("xAuthToken", res.json().token);
  			this.loggedIn = true;
  			location.reload();
  			this.router.navigate(['/home']);
  		},
  		err =>{
  			this.loggedIn = false;
  			this.loginError = true;
        this.dataLoading = false;
  		}
  	);
  }



  ngOnInit() {
  	this.loginService.checkSession().subscribe(
  		res => {
  			this.loggedIn = true;
  		},
  		err =>{
  			this.loggedIn = false;
  		}
  	);
  }

}

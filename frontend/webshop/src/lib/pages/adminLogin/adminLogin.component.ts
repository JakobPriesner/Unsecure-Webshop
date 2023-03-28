import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../data-access/service/authentication.service";
@Component({
  selector: 'admin-login',
  templateUrl: './adminLogin.component.html',
  styleUrls: ['./adminLogin.component.scss']
})
export class AdminLoginComponent {

  username: string = "";
  password: string = "";

  invalidData: boolean = false;

  constructor(private authenticationService: AuthenticationService, private router: Router) {
  }

  onLogin() {
    this.authenticationService.adminLogin(this.username, this.password).subscribe(() => this.router.navigateByUrl('/admin'), (error) => this.invalidData = true);
  }
}

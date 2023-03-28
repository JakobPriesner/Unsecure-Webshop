import {Component, Input, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../data-access/service/authentication.service";
import {UserTypes} from "../../data-access/enums/userTypes";
import {CookieService} from "ngx-cookie-service";
import {AdminAuthenticationGuard} from "../../data-access/service/canActivateGuards/adminAuthentication.guard";

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  @Input() title?: String;
  login: boolean = false;
  loginAdmin: boolean = false;
  searchInput: string = "";

  constructor(private authenticationService: AuthenticationService, private router: Router, private cookieService: CookieService, private adminAuthentication: AdminAuthenticationGuard) {
  }

  ngOnInit() {
    this.authenticationService.getStatus().subscribe(
      status => {
        this.login = status == UserTypes.User;
        this.loginAdmin = status == UserTypes.Admin;
      }
    )
  }

  onLogOut(): void {
    if (this.cookieService.get("userType") == UserTypes.User.toString()) {
      this.authenticationService.logout().subscribe();
    } else {
      this.authenticationService.adminLogout().subscribe();
    }
    this.router.navigateByUrl('/index');
  }

  onSearchClick() {
    if (this.searchInput != "") {
      this.router.navigateByUrl('/articles?search=' + this.searchInput);
    }
  }
}

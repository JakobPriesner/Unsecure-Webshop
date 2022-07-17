import {Component, Input, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../data-access/service/authentication.service";
import {UserTypes} from "../../data-access/enums/userTypes";

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

  constructor(private authenticationService: AuthenticationService, private router: Router) {
  }

  ngOnInit() {
    this.authenticationService.getStatus().subscribe(
      status => {
        this.login = status;
      }
    );
    this.authenticationService.getAdminStatus().subscribe(
      status => {
        this.loginAdmin = status;
      }
    );
  }

  onLogOut(): void {
    if (this.authenticationService.userType == UserTypes.User) {
      this.authenticationService.logout().subscribe();
    } else {
      this.authenticationService.adminLogout().subscribe();
    }
    this.router.navigateByUrl('/index');
  }

  onSearchClick() {
    if (this.searchInput != "") {
      this.router.navigateByUrl!('/articles?search=' + this.searchInput);
    }
  }
}

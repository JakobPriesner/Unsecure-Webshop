import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {CookieService} from "ngx-cookie-service";
import {AuthenticationService} from "../authentication.service";
import {UserTypes} from "../../enums/userTypes";

@Injectable({providedIn: 'root'})
export class AdminAuthenticationGuard implements CanActivate {

  constructor(private router: Router, private cookieService: CookieService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if (this.cookieService.check('sessionKey') && this.cookieService.get("userType") == "" + UserTypes.Admin.valueOf()) {
      return true;
    }
    this.router.navigate!(['/adminLogin'], {queryParams: {returnUrl: state.url}});
    return false;
  }
}

import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {map, Observable, ReplaySubject} from "rxjs";
import {BackendService} from "./backend.service";
import {AddressStore} from "./store/address.store";
import {OrderStore} from "./store/order.store";
import {ShoppingCartStore} from "./store/shoppingCart.store";
import {UserStore} from "./store/user.store";
import {WishlistStore} from "./store/wishlist.store";
import {UserTypes} from "../enums/userTypes";
import {CookieService} from "ngx-cookie-service";
import {Statics} from "./statics";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  statusSubject: ReplaySubject<number> = new ReplaySubject<number>(1);
  header: HttpHeaders = new HttpHeaders();

  constructor(
    public backendService: BackendService,
    private httpClient: HttpClient,
    private addressStore: AddressStore,
    private orderStore: OrderStore,
    private shoppingCartStore: ShoppingCartStore,
    private userStore: UserStore,
    private wishlistStore: WishlistStore,
    private cookieService: CookieService
  ) {
    if (this.cookieService.check("sessionKey")) {
      if (this.cookieService.get("userType") == "" + UserTypes.Admin) {
        this.statusSubject.next(UserTypes.Admin);
      } else {
        this.statusSubject.next(UserTypes.User)
      }
    } else {
      this.statusSubject.next(-1);
    }
  }

  login(mail: string, password: string): Observable<boolean> {
    return this.httpClient.get(Statics.url + 'users/login?mail=' + mail + '&password=' + password, {
      observe: "body", responseType: "text", headers: this.backendService.getHeader()
    }).pipe(
      map(sessionKey => {
        this.cookieService.set('sessionKey', sessionKey);
        this.backendService.sessionKey = sessionKey;
        this.statusSubject.next(UserTypes.User);
        this.cookieService.set("userType", UserTypes.User.toString());
        return true;
      })
    );
  }

  logout(): Observable<any> {
    if (this.cookieService != undefined) {
      let sessionKey = this.cookieService.get('sessionKey').replace('sessionKey=', '');

      let response: Observable<any> = this.httpClient.post(Statics.url + 'users/logout', {sessionKey}, {
        headers: this.backendService.getHeader(),
        observe: "response",
      });
      this.cookieService.delete('sessionKey');
      this.backendService.sessionKey = "";
      this.statusSubject.next(-1);
      this.cookieService.set("userType", UserTypes.User.toString());
      this.cleanupStores();
      return response;
    }
    return new Observable<any>();
  }

  adminLogin(username: string, password: string): Observable<any> {
    return this.httpClient.get(Statics.url + 'admin/login?username=' + username + '&password=' + password, {
      observe: "body", responseType: "text", headers: this.backendService.getHeader()
    }).pipe(
      map(sessionKey => {
        this.cookieService.set("sessionKey", sessionKey);
        this.backendService.sessionKey = sessionKey;
        this.statusSubject.next(UserTypes.Admin);
        this.cookieService.set("userType", UserTypes.Admin.toString());
      })
    );
  }

  adminLogout(): Observable<any> {
    if (this.cookieService != undefined) {
      let sessionKey = this.cookieService.get('sessionKey').replace('sessionKey=', '');

      let response: Observable<any> = this.httpClient.post(Statics.url + 'admin/logout', {sessionKey}, {
        headers: this.backendService.getHeader(),
        observe: "response"
      });
      this.cookieService.delete('sessionKey');
      this.statusSubject.next(-1);
      this.backendService.sessionKey = "";
      this.cookieService.set("userType", UserTypes.User.toString());
      this.cleanupStores();
      return response;
    }
    return new Observable<any>();

  }

  register(firstname: string, lastname: string, mail: string, password: string): Observable<any> {
    return this.httpClient.post(Statics.url + 'users/register', {
      "firstName": firstname,
      "lastName": lastname,
      "mail": mail,
      "password": password
    }, {observe: "body", responseType: "text", headers: this.backendService.getHeader()}).pipe(
      map(sessionKey => {
        this.cookieService.set("sessionKey", sessionKey);
        this.statusSubject.next(UserTypes.User);
        this.backendService.sessionKey = sessionKey;
        return sessionKey;
      })
    );
  }

  getStatus(): ReplaySubject<number> {
    return this.statusSubject;
  }

  cleanupStores(): void {
    this.addressStore.cleaningUp();
    this.orderStore.cleaningUp();
    this.shoppingCartStore.cleaningUp();
    this.userStore.cleaningUp();
    this.wishlistStore.cleaningUp();
    this.cookieService.set("userType", UserTypes.User.toString());
  }
}

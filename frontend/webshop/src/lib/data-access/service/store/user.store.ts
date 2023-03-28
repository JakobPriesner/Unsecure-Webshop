import {Injectable} from "@angular/core";
import {User} from "../../models";
import {BackendService} from "../backend.service";
import {Observable, ReplaySubject} from "rxjs";
import {CookieService} from "ngx-cookie-service";
import {Nletter} from "../../models/nletter";

@Injectable({
  providedIn: 'root'
})
export class UserStore {

  // @ts-ignore
  user: User | undefined;
  userSubject: ReplaySubject<User | undefined> = new ReplaySubject<User | undefined>(1);

  constructor(private backendService: BackendService, private cookieService: CookieService) {
  }

  loadUser(): ReplaySubject<User | undefined> {
    if (!this.user || this.cookieService.get('sessionKey') == null) {
      this.backendService.loadUser().subscribe(user => {
        this.user = user;
        this.userSubject.next(this.user);
      });
    } else {
      this.userSubject.next(this.user);
    }
    return this.userSubject;
  }

  updateUser(user: User, oldPassword: string = "", newPassword: string = ""): void {
    this.user = user;
    this.userSubject.next(this.user);
    let userPayload: any = {
      ...user
    }
    if (oldPassword != "" && newPassword != "") {
      userPayload = {
        ...user,
        "oldPassword": oldPassword,
        "newPassword": newPassword
      }
    }
    this.backendService.updateUser(userPayload).subscribe(user => {
      this.user = user;
      this.userSubject.next(this.user);
    });
  }

  getUser(): ReplaySubject<User | undefined> {
    if (this.user == undefined) {
      this.backendService.loadUser().subscribe(user => this.user = user);
      this.userSubject.next(this.user);
    } else {
      this.userSubject.next(this.user);
    }
    return this.userSubject;
  }

  getNewsletterStatus(): Observable<boolean> {
    return this.backendService.getNewsletterStatus();
  }

  subscribeNewsletter(nletter: Nletter): Observable<any> {
    this.user!.newsletter = true;
    return this.backendService.postNewsletter(nletter);
  }

  unsubscribeNewsletter(): Observable<any> {
    this.user!.newsletter = false;
    return this.backendService.deleteNewsletter();
  }

  cleaningUp(): void {
    this.user = undefined;
    this.userSubject.next(this.user);
  }
}

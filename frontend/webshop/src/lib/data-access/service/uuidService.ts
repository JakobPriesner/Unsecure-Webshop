import {Injectable} from "@angular/core";
import {CookieService} from "ngx-cookie-service";
import {Observable, ReplaySubject} from "rxjs";
import {Uuid} from "../models/uuid";
import {HttpClient} from "@angular/common/http";
import {Statics} from "./statics";

@Injectable({providedIn: 'root'})
export class UuidService {

  uuid: ReplaySubject<string> = new ReplaySubject<string>(1);

  constructor(private cookieService: CookieService, private httpClient: HttpClient) {
    if (this.cookieService.check('uuid')) {
      this.uuid.next(this.cookieService.get('uuid').replace('uuid=', ''));
    } else {
      this.getNewUuid().subscribe(uuid => {
        this.uuid.next(uuid.encoded);
        this.cookieService.set('uuid', uuid.encoded, 1);
      });
    }
  }

  getNewUuid(): Observable<Uuid> {
    return this.httpClient.get<Uuid>(Statics.url + 'users/me');
  }
}

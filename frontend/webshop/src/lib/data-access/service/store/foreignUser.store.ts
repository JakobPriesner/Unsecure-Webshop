import {Injectable} from "@angular/core";
import {User} from "../../models";
import {BackendService} from "../backend.service";
import {ReplaySubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ForeignUserStore {

  // @ts-ignore
  users: User[] = [];

  constructor(private backendService: BackendService) {
  }

  loadUserById(id: number): ReplaySubject<User> {
    const userSubject: ReplaySubject<User> = new ReplaySubject<User>(1);
    let index: number = this.users.findIndex(user => user.id === id);

    if (index == -1) {
      this.backendService.loadUserById(id).subscribe(user => {
        this.users?.push(user);
        userSubject.next(user);
      });
    } else {
      userSubject.next(this.users[index]);
    }
    return userSubject;
  }
}

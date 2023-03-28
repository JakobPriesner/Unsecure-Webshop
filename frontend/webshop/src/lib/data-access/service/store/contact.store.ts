import {Contact, Nletter} from "../../models";
import {BackendService} from "../backend.service";
import {Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class ContactStore {
  // @ts-ignore
  contact: Contact;

  constructor(private backendService: BackendService) {
  }

  createContact(contact: Contact): Observable<any> {
    return this.backendService.postContact(contact);
  }


}


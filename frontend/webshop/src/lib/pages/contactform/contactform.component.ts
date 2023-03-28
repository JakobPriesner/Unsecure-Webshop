import {Component} from "@angular/core";
import {Contact} from "../../data-access/models";
import {ContactStore} from "../../data-access/service/store/contact.store";
import {Router} from "@angular/router";

@Component(
  {
    selector: 'contactform',
    templateUrl: './contactform.component.html',
    styleUrls: ['./contactform.component.scss']
  }
)

export class ContactformComponent {

  // @ts-ignore
  contact: Contact = {firstName: '', lastName: '', mail: '', message: ''};
  requiredInput: boolean = true;
  checkBox: boolean = false;

  constructor(private contactStore: ContactStore, private router: Router) {
  }

  onSubmit(): void {
    if(this.contact.firstName == '' || this.contact.lastName == '' || this.contact.mail == '' || this.contact.message == ''|| !this.checkBox)
    {
      this.requiredInput = false;
    }
    else{
      this.requiredInput = true;
      if(this.requiredInput && this.checkBox) {
        this.contactStore.createContact(this.contact).subscribe(() => this.router.navigateByUrl('/index'));
      }
    }

  }

}

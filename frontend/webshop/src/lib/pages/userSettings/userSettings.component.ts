import {Component, ElementRef, OnInit, SecurityContext, ViewChild} from "@angular/core";
import {Address, User} from "../../data-access/models";
import {UserStore} from "../../data-access/service/store/user.store";
import {AddressStore} from "../../data-access/service/store/address.store";
import {DomSanitizer, SafeHtml} from "@angular/platform-browser";
import {Nletter} from "../../data-access/models";

@Component({
  selector: 'user-settings',
  templateUrl: './userSettings.component.html',
  styleUrls: ['./userSettings.component.scss']
})
export class UserSettingsComponent implements OnInit {
  // @ts-ignore
  user: User;
  // @ts-ignore
  addresses: Address[];

  oldPassword: string = '';
  newPassword: string = '';
  newPasswordConfirm: string = '';

  matchingNewPasswords: boolean = true;
  fulfillsPasswordRequirements: boolean = true;
  validOldPassword: boolean = true;

  newsletter: boolean = false;
  nletter: Nletter = {email: ''}

  // @ts-ignore
  @ViewChild('descriptionField') descriptionRef: ElementRef;

  constructor(private userStore: UserStore, private addressStore: AddressStore, private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.userStore.loadUser().subscribe(user => {
      if (user) {
        if( user.description == undefined ) {
          user.description = "";
        }
        this.user = user;
        setTimeout(() => {
          this.getDescription();
        }, 2000);
      }
    });
    this.addressStore.loadAllAddresses().subscribe(addresses => {
      this.addresses = addresses
    });

    this.userStore.getNewsletterStatus().subscribe(respond => {
      this.newsletter = respond.valueOf();
    });
  }

  onNewsletter(mail: string) {
    this.nletter.email = mail;
    this.newsletter = !this.newsletter;
    this.newsletter ? this.userStore.subscribeNewsletter(this.nletter).subscribe() : this.userStore.unsubscribeNewsletter().subscribe();
  }

  getUserName(): string {
    return this.user.firstName + " " + this.user.lastName;
  }

  getDescription(): void {
    let content = document.getElementById("descriptionField");
    if ( content != undefined ) {
      content!.replaceChildren();
      content!.appendChild(document.createRange().createContextualFragment(this.user.description!));
    }
  }

  validateNewPasswordEightChars(): boolean {
    return this.newPassword.length >= 8;
  }

  validateNewPasswordSpecialChars(): boolean {
    let regex = /[`!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/g;
    return regex.test(this.newPassword);
  }

  validateNewPasswordOneNumber(): boolean {
    let regex = /\d+/g;
    return regex.test(this.newPassword);
  }

  onAddUserAddress(): void {
    this.addressStore.createAddress({
      id: 1,
      name: "",
      address: "",
      address2: "",
      city: "",
      zipCode: -1,
      country: "",
      deliveryInstructions: ""
    });
  }

  onUpdateProfilePicture(event: any): void {
    let fileReader = new FileReader();
    fileReader.readAsDataURL(event.target.files[0]);
    fileReader.onloadend = () => {
      if (fileReader.result) {
        let sanitizedImage = this.sanitizer.sanitize(SecurityContext.URL, fileReader.result.toString());
        if (sanitizedImage) {
          this.user.profilePicture = sanitizedImage.toString();

        }
        this.userStore.updateUser(this.user);
      }
    }
  }

  onSubmit(): void {
    if (this.newPassword != "") {
      if (this.newPassword != this.newPasswordConfirm) {
        this.matchingNewPasswords = false;
        return;
      } else {
        this.matchingNewPasswords = true;
      }
      if (!(this.validateNewPasswordSpecialChars()
        && this.validateNewPasswordOneNumber()
        && this.validateNewPasswordEightChars())) {
        this.fulfillsPasswordRequirements = false;
        return;
      } else {
        this.fulfillsPasswordRequirements = true;
      }
    }

    try {
      this.userStore.updateUser(this.user, this.oldPassword, this.newPassword);
      this.matchingNewPasswords = true;
      this.fulfillsPasswordRequirements = true;
      this.validOldPassword = true;
    } catch (Exception) {
      this.validOldPassword = false;
    }
  }


}

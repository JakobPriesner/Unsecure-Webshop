import {Component, ElementRef, OnInit, SecurityContext, ViewChild} from "@angular/core";
import {Address, Nletter, User} from "../../data-access/models";
import {UserStore} from "../../data-access/service/store/user.store";
import {AddressStore} from "../../data-access/service/store/address.store";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'user-settings',
  templateUrl: './userSettings.component.html',
  styleUrls: ['./userSettings.component.scss']
})
export class UserSettingsComponent implements OnInit {

  user: User | undefined;
  addresses: Address[] | undefined;

  oldPassword: string = '';
  newPassword: string = '';
  newPasswordConfirm: string = '';

  matchingNewPasswords: boolean = true;
  fulfillsPasswordRequirements: boolean = true;
  validOldPassword: boolean = true;

  subscribedToNewsletter: boolean = false;
  newsletter: Nletter = {email: ''}

  @ViewChild('descriptionField') descriptionRef: ElementRef | undefined;

  constructor(private userStore: UserStore, private addressStore: AddressStore, private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.userStore.loadUser().subscribe(user => {
      if (user) {
        user.description = user.description ?? "";
        this.user = user;
        // toDo
        setTimeout(() => {
          this.getDescription();
        }, 2000);
      }
    });
    this.addressStore.loadAllAddresses().subscribe(addresses => {
      this.addresses = addresses
    });

    this.userStore.getNewsletterStatus().subscribe(respond => this.subscribedToNewsletter = respond.valueOf());
  }

  onNewsletter(mail: string) {
    this.newsletter.email = mail;
    this.subscribedToNewsletter = !this.subscribedToNewsletter;
    this.subscribedToNewsletter ? this.userStore.subscribeNewsletter(this.newsletter) : this.userStore.unsubscribeNewsletter();
  }

  getUserName(): string {
    return `${this.user!.firstName} ${this.user!.lastName}`;
  }

  getDescription(): void {
    let content = document.getElementById("descriptionField");
    console.log(content);
    if (content && this.user!.description) {
      content!.replaceChildren();
      content!.appendChild(document.createRange().createContextualFragment(this.user!.description));
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
        let sanitizedImage = this.sanitizer.sanitize(SecurityContext.URL, fileReader.result.toString()) ?? this.user!.profilePicture;
        if (sanitizedImage) this.user!.profilePicture = sanitizedImage.toString();
        this.userStore.updateUser(this.user!);
      }
    }
  }

  onSubmit(): void {
    if (this.newPassword != "") {
      this.matchingNewPasswords = this.newPassword == this.newPasswordConfirm;
      this.fulfillsPasswordRequirements = this.validateNewPassword();
      
      if (!this.matchingNewPasswords || !this.fulfillsPasswordRequirements) return;
    }

    try {
      this.userStore.updateUser(this.user!, this.oldPassword, this.newPassword);
      this.matchingNewPasswords = true;
      this.fulfillsPasswordRequirements = true;
      this.validOldPassword = true;
    } catch (Exception) {
      this.validOldPassword = false;
    }
  }

  private validateNewPassword(): boolean {
    return (this.validateNewPasswordSpecialChars()
      && this.validateNewPasswordOneNumber()
      && this.validateNewPasswordEightChars());
  }


}

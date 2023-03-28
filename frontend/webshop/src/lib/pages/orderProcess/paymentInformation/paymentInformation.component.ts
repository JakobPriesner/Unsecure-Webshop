import {Component, EventEmitter, Input, Output} from "@angular/core";
import {Payment} from "../../../data-access/models";

@Component({
  selector: 'payment-information',
  templateUrl: './paymentInformation.component.html',
  styleUrls: ['./paymentInformation.component.scss']
})
export class PaymentInformationComponent {
  // @ts-ignore
  @Input order;

  @Output() onBuyEvent: EventEmitter<Payment> = new EventEmitter<Payment>();

  iban: string = "";
  bic: string = "";
  accountHolder: string = "";

  invalidData: boolean = false;

  updateIbanValue(): void {
    this.iban = this.iban.replace(/[^\dA-Z]/g, '').replace(/(.{4})/g, '$1 ').trim();
  }

  onBuy(): void {
    if (!this.iban || !this.bic || !this.accountHolder) {
      this.invalidData = true;
    } else {
      this.onBuyEvent.emit({iban: this.iban, bic: this.bic, accountHolder: this.accountHolder});
    }

  }
}

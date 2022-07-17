import {Component, EventEmitter, Input, Output} from "@angular/core";
import {Payment} from "../../../data-access/models";
import Order = jasmine.Order;

@Component({
  selector: 'payment-information',
  templateUrl: './paymentInformation.component.html',
  styleUrls: ['./paymentInformation.component.scss']
})
export class PaymentInformationComponent {

  @Input() order: Order | undefined;
  @Output() onBuyEvent: EventEmitter<Payment> = new EventEmitter<Payment>();

  iban: string = "";
  bic: string = "";
  accountHolder: string = "";

  invalidData: boolean = false;

  updateIbanValue(): void {
    this.iban = this.iban.replace(/[^\dA-Z]/g, '').replace(/(.{4})/g, '$1 ').trim();
  }

  onBuy(): void {
    if (this.invalidInputData()) {
      this.invalidData = true;
    } else {
      this.onBuyEvent.emit({iban: this.iban, bic: this.bic, accountHolder: this.accountHolder});
    }
  }

  private invalidInputData(): boolean {
    return !this.iban || !this.bic || !this.accountHolder;
  }

}

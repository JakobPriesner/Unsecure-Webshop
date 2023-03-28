import {Component, EventEmitter, OnInit, Output} from "@angular/core";
import {Address} from "../../../data-access/models";
import {AddressStore} from "../../../data-access/service/store/address.store";

@Component({
  selector: 'order-address',
  templateUrl: './orderAddress.component.html',
  styleUrls: ['./orderAddress.component.scss']
})
export class OrderAddressComponent implements OnInit {
  @Output() onSelectAddressEvent: EventEmitter<Address> = new EventEmitter<Address>();
  @Output() onNextPageEvent: EventEmitter<any> = new EventEmitter<any>();

  addresses: Address[] = [];
  pageSelected: boolean = false;
  invalidData: boolean = false;

  constructor(private addressStore: AddressStore) {
  }

  ngOnInit() {
    this.addressStore.loadAllAddresses().subscribe(addresses => this.addresses = addresses);
  }

  onClickOnAddress(address: Address): void {
    this.pageSelected = true;
    this.onSelectAddressEvent.emit(this.addresses.find(a => a.id === address.id));
  }

  onClickOnNextPage(): void {
    if (!this.pageSelected) {
      this.invalidData = true;
      return;
    }
    this.onNextPageEvent.emit()
  }

  onAddAddress(): void {
    let address = {name: "", address: "", address2: "", zipCode: -1, city: "", country: "", deliveryInstructions: ""}
    this.addressStore.createAddress(address).subscribe(addresses => {
      this.addresses = addresses;
      // toDo: auto select address
    });
  }

}

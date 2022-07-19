import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {Address} from "../../data-access/models";
import {AddressStore} from "../../data-access/service/store/address.store";

@Component({
  selector: 'addresses',
  templateUrl: './addressList.component.html',
  styleUrls: ['./addressList.component.scss']
})
export class AddressListComponent implements OnInit {
  @Input() addresses: Address[] = [];
  @Input() selectable: boolean = false;
  @Output() addAddressEvent: EventEmitter<any> = new EventEmitter<any>();
  @Output() onSelectAddressEvent: EventEmitter<Address> = new EventEmitter<Address>();

  selectedAddress: Address | undefined;

  constructor(private addressStore: AddressStore) {
  }

  ngOnInit() {
    if (this.addresses == []) {
      this.addressStore.loadAllAddresses().subscribe(addresses => {
        this.addresses = addresses;
      });
    }
  }

  onAddAddress(): void {
    this.addressStore.createAddress({
      name: "",
      address: "",
      address2: "",
      zipCode: -1,
      city: "",
      country: "",
      deliveryInstructions: ""
    }).subscribe()
  }

  onSelectAddress(address: Address): void {
    this.onSelectAddressEvent.emit(address);
  }

}

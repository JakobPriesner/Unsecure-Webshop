import {Component, Input, TemplateRef} from "@angular/core";
import {Address} from "../../../data-access/models";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {AddressStore} from "../../../data-access/service/store/address.store";
import {AddressListComponent} from "../addressList.component";

@Component({
  selector: 'address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.scss']
})
export class AddressComponent {
  // @ts-ignore
  @Input() address: Address;
  @Input() selectable: boolean = false;
  // @ts-ignore
  @Input() addressList: AddressListComponent;

  modalRef?: BsModalRef;

  constructor(private modalService: BsModalService,
              private addressStore: AddressStore) {
  }

  onEditAddressEvent(template: TemplateRef<any>): void {
    this.modalRef = this.modalService.show(template, {animated: true, keyboard: true});
  }

  onCancel(): void {
    if (this.address.id) {
      this.addressStore.loadAddressById(this.address.id).subscribe(address => this.address = address);
      this.modalRef?.hide();
    }
  }

  onSubmit(): void {
    this.addressStore.updateAddress(this.address);
    this.modalRef?.hide();
  }

  isSelected(): boolean {
    return this.address == this.addressList.selectedAddress;
  }

  onSelect(): void {
    this.addressList.selectedAddress = this.address;
  }
}

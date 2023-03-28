import {Component, OnInit} from "@angular/core";
import {ShoppingCartStore} from "../../data-access/service/store/shoppingCart.store";
import {Address, Order, Payment, SpecifiedItem} from "../../data-access/models";
import {AddressStore} from "../../data-access/service/store/address.store";
import {OrderStore} from "../../data-access/service/store/order.store";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'order-process',
  templateUrl: './orderProcess.component.html',
  styleUrls: ['./orderProcess.component.scss']
})
export class OrderProcessComponent implements OnInit {

  itemsList: SpecifiedItem[] = [];
  addresses: Address[] = [];

  // @ts-ignore
  selectedAddress: Address;

  // @ts-ignore
  paymentInformation: Payment;

  // @ts-ignore
  order: Order;

  coupon: string = "";

  currentStep: number = 0;
  reachedStep: number = 0;

  invalidData: boolean = false;
  amount: string|undefined;

  constructor(private shoppingCartStore: ShoppingCartStore, private addressStore: AddressStore,
              private orderStore: OrderStore, private route: ActivatedRoute ) {
    this.route.queryParams.subscribe( params =>  {
      this.amount = params['amount'];
    });
  }

  ngOnInit() {
    this.shoppingCartStore.loadShoppingCart().subscribe(items => {
      this.itemsList = items;
    });
    this.addressStore.loadAllAddresses().subscribe(addresses => this.addresses = addresses);
  }

  changeStep(selectedStep: number): void {
    // bin 0, reached 1, möchte 2
    if (selectedStep < this.reachedStep) {
      this.currentStep = selectedStep;
    } else if (selectedStep <= this.reachedStep + 1) {
      switch (this.reachedStep) {
        case 1:
          if (this.selectedAddress == undefined) {
            this.invalidData = true;
            this.currentStep = this.reachedStep;
            return;
          }
          break;
        case 2:
          if (this.paymentInformation == undefined) {
            this.invalidData = true;
            this.currentStep = this.reachedStep;
            return;
          }
          break;
        default:
          break;
      }
      if (selectedStep == this.reachedStep + 1)
        this.reachedStep++;
      this.invalidData = false;
      this.onNextPage();
    }
  }

  getItemList(): SpecifiedItem[] {
    return this.itemsList;
  }

  onNextPage(): void {
    this.currentStep++;
    if (this.currentStep > this.reachedStep) {
      this.reachedStep = this.currentStep;
    }
  }

  onBuy(): void {
    this.orderStore.postOrder({
      specifiedItems: this.itemsList,
      coupon: {name: this.coupon},
      address: this.selectedAddress,
      payment: this.paymentInformation
    }, this.getValidAmount() ).subscribe(path => {
      this.orderStore.loadOrderById(this.getIdOfPath(path)).subscribe(order => this.order = order);

      this.currentStep++;
      if (this.currentStep > this.reachedStep) {
        this.reachedStep = this.currentStep;
      }
    });
  }

  getValidAmount(): number {
    if( this.amount != undefined )
    {
      let number = Number(this.amount);
      if ( isNaN( number ) )
      {
        return -1;
      } else {
        return number;
      }
    } else {
     return -1;
    }
  }

  getIdOfPath(path: string): number {
    return +path.substring(path.lastIndexOf("/") + 1);
  }

  onUpdateCoupon(coupon: string): void {
    this.coupon = coupon;
  }

  getOrderItemsClasses(): string {
    let output: string = this.reachedStep >= 0 ? 'active' : '';
    return this.currentStep < 3 ? output + ' disabled' : output;
  }

  getAddressClasses(): string {
    let output: string = this.reachedStep >= 1 ? 'active' : '';
    return this.currentStep > 0 && this.currentStep < 3 ? output + ' disabled' : output;
  }

  getPaymentClasses(): string {
    let output: string = this.reachedStep >= 2 ? 'active' : '';
    return this.currentStep > 1 && this.currentStep < 3 ? output + ' disabled' : output;
  }

  getConfirmationClasses(): string {
    let output: string = this.reachedStep >= 3 ? 'active' : '';
    return this.currentStep < 3 ? output + ' disabled' : output;
  }
}

import {Component, EventEmitter, Input, Output} from "@angular/core";
import {Coupon, SpecifiedItem} from "../../../data-access/models/";
import {ShoppingCartStore} from "../../../data-access/service/store/shoppingCart.store";
import {BackendService} from "../../../data-access/service/backend.service";

@Component({
  selector: 'order-items',
  templateUrl: './orderItems.component.html',
  styleUrls: ['./orderItems.component.scss']
})
export class OrderItemsComponent {

  @Input() itemList: SpecifiedItem[] | undefined;

  @Output() onNextPageEvent: EventEmitter<any> = new EventEmitter<any>();
  @Output() onUpdateCouponEvent: EventEmitter<string> = new EventEmitter<string>();

  coupon: Coupon = {name: ""};
  couponPercent: number = 0;
  invalidCoupon: boolean = false;

  constructor(private shoppingCartStore: ShoppingCartStore, private backendService: BackendService) {
  }

  onItemDelete(itemId: number): void {
    this.shoppingCartStore.deleteItem(itemId);
  }

  onUpdateItemQuantity(itemId: number, quantity: number): void {
    this.shoppingCartStore.updateItem(itemId, quantity);
  }

  public onNextPageClick(): void {
    this.onUpdateCouponEvent.emit(this.coupon.name);
    this.onNextPageEvent.emit();
  }

  onUpdateCoupon(): void {
    this.backendService.getCoupon(this.coupon.name).subscribe(coupon => {
      this.coupon = coupon;
      this.invalidCoupon = false;
    }, () => {
      this.invalidCoupon = true;
    })
  }

  getAmount(): number {
    let totalAmount: number = 0;
    this.itemList?.forEach(item => {
      totalAmount += (item.amount ?? 0) * (item.quantity ?? 0);
    })
    return totalAmount;
  }

  getTotalAmount(): number {
    let totalAmount: number = this.getAmount();
    totalAmount -= (this.coupon.percent ?? 0) * totalAmount / 100;
    return totalAmount;
  }

  getPercentAmount(): number {
    return (this.coupon.percent ?? 0) * this.getAmount() / 100;
  }

  getCouponButtonText(): string {
    if (this.coupon.percent) return "ändern";
    else return 'einlösen';
  }
}

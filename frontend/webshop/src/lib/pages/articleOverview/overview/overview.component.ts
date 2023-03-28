import {Component, EventEmitter, Input, Output} from "@angular/core";
import {Article} from "../../../data-access/models/article";

@Component({
  selector: 'article-overview-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss']
})
export class OverviewComponent {
  // @ts-ignore
  @Input article: Article;

  // @ts-ignore
  quntity: number = 1;

  @Output() changeQuantityEvent: EventEmitter<number> = new EventEmitter<number>();
  @Output() addToShoppingCartEvent: EventEmitter<any> = new EventEmitter<any>();
  @Output() addToWishListEvent: EventEmitter<any> = new EventEmitter<any>();

  onChangeQuantity(): void {
    this.changeQuantityEvent.emit(this.quntity);
  }

  onAddToWishList(): void {
    this.addToWishListEvent.emit();
  }

  onAddToShoppingCart(): void {
    this.addToShoppingCartEvent.emit();
  }
}


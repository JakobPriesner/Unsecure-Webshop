import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {SpecifiedItem} from "src/lib/data-access/models";
import {ImageStore} from "../../../data-access/service/store/image.store";
import {Router} from "@angular/router";
import {ShoppingCartStore} from "../../../data-access/service/store/shoppingCart.store";

@Component({
  selector: 'specified-item',
  templateUrl: './specifiedItem.component.html',
  styleUrls: ['./specifiedItem.component.scss']
})
export class SpecifiedItemComponent implements OnInit {
  @Input() showDeleteButton: boolean = true;
  @Input() editableQuantity: boolean = true;
  // @ts-ignore
  @Input() specifiedItem: SpecifiedItem;
  @Input() showAddCartButton: boolean = false;

  @Output() onDeleteEvent: EventEmitter<number> = new EventEmitter<number>();
  @Output() onQuantityChangeEvent: EventEmitter<{ itemId: number, quantity: number }> = new EventEmitter<{ itemId: number, quantity: number }>();

  // @ts-ignore
  picture: string;
  showTrash: boolean = false;

  constructor(private imageStore: ImageStore, private routing: Router, private cartStore: ShoppingCartStore) {
  }

  ngOnInit() {
    if (this.specifiedItem.pictureId)
      this.imageStore.loadImageById(this.specifiedItem.pictureId).subscribe(picture => {
        this.picture = picture;
      });
  }

  onItemDelete(): void {
    this.onDeleteEvent.emit(this.specifiedItem.id);
  }

  onItemChange(event: any): void {
    if (this.specifiedItem.id) {
      this.onQuantityChangeEvent.emit({itemId: this.specifiedItem.id, quantity: event.target.value});
    }
  }

  onMouseEnter(): void {
    this.showTrash = true;
  }

  onMouseLeave(): void {
    this.showTrash = false;
  }

  onAddToShoppingCart() {
    this.cartStore.addItem(this.specifiedItem).subscribe();
    this.onItemDelete();
    this.routing.navigateByUrl('/cart');
  }
}

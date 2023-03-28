import {Component, EventEmitter, Input, Output} from "@angular/core";
import {SpecifiedItem} from "src/lib/data-access/models";

@Component({
  selector: 'specified-item-list',
  templateUrl: './specifiedItemList.component.html',
  styleUrls: ['./specifiedItemList.component.scss']
})
export class SpecifiedItemListComponent {
  @Input() showDeleteButton: boolean = true;
  @Input() editableQuantity: boolean = true;
  @Input() itemList: SpecifiedItem[] = [];
  @Input() showAddCartButton: boolean = false;

  @Output() onDeleteEvent: EventEmitter<number> = new EventEmitter<number>();
  @Output() onQuantityChangeEvent: EventEmitter<{ itemId: number, quantity: number }> = new EventEmitter<{ itemId: number, quantity: number }>();

  onItemDelete(itemId: number): void {
    this.onDeleteEvent.emit(itemId);
  }

  onItemChange(event: any): void {
    this.onQuantityChangeEvent.emit(event);
  }
}

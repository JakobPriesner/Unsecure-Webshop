import {Component, OnInit} from "@angular/core";
import {WishlistStore} from "../../data-access/service/store/wishlist.store";
import {SpecifiedItem} from "../../data-access/models";

@Component({
  selector: 'wish-list',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.scss']
})
export class WishlistComponent implements OnInit {
  itemList: SpecifiedItem[] = [];
  showText: boolean = false;

  constructor(private wishlistStore: WishlistStore) {
  }

  onWishListDelete(): void {
    this.wishlistStore.deleteWishlist();
  }

  onItemDelete(itemId: number): void {
    this.wishlistStore.deleteItem(itemId);
  }

  onItemChange(event: any): void {
    this.wishlistStore.updateItem(event.itemId, event.quantity);
  }

  ngOnInit(): void {
    this.wishlistStore.loadWishList().subscribe(itemList => this.itemList = itemList)
  }
}

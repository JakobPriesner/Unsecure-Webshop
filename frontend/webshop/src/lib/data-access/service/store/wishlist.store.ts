import {Injectable} from "@angular/core";
import {ReplaySubject} from "rxjs";
import {BackendService} from "../backend.service";
import {SpecifiedItem} from "../../models";

@Injectable({
  providedIn: 'root'
})
export class WishlistStore {
  itemList: SpecifiedItem[] = [];
  wishListSubject: ReplaySubject<SpecifiedItem[]> = new ReplaySubject<SpecifiedItem[]>(1);

  constructor(private backendService: BackendService) {

  }

  loadWishList(): ReplaySubject<SpecifiedItem[]> {
    if (this.itemList.length == 0) {
      this.backendService.loadWishList().subscribe(specifiedItems => {
        this.itemList = specifiedItems;
        this.wishListSubject.next(this.itemList);
      });
    } else {
      this.wishListSubject.next(this.itemList)
    }
    return this.wishListSubject;
  }

  // updateItems(items: SpecifiedItem[]): ReplaySubject<SpecifiedItem[]> {
  //   this.specifiedItems = items;
  //   this.wishListSubject.next(this.specifiedItems);
  //   this.backendService.updateWishList(this.specifiedItems).subscribe();
  //   return this.wishListSubject;
  // }

  updateItem(itemId: number, newQuantity: number): ReplaySubject<SpecifiedItem[]> {
    let index = this.itemList.findIndex(i => itemId == i.id);
    this.itemList[index].quantity = newQuantity;
    this.wishListSubject.next(this.itemList);
    this.backendService.updateWishListItem(this.itemList[index]).subscribe( () =>
      this.backendService.loadWishList().subscribe( items => {
        this.itemList = items;
        this.wishListSubject.next(this.itemList)
      })
    );
    return this.wishListSubject;
  }

  addItem(item: SpecifiedItem): ReplaySubject<SpecifiedItem[]> {
    this.itemList.push(item);
    this.backendService.addItemToWishList(item).subscribe( () =>
      this.backendService.loadWishList().subscribe( items => {
        this.itemList = items;
        this.wishListSubject.next(this.itemList);
      })
    );
    return this.wishListSubject;
  }

  deleteItem(itemId: number): ReplaySubject<any> {
    let index = this.itemList.findIndex(i => itemId == i.id);
    this.itemList.splice(index, 1);
    this.wishListSubject.next(this.itemList);
    this.backendService.deleteWishListItem(itemId).subscribe();
    return this.wishListSubject;
  }

  deleteWishlist(): ReplaySubject<any> {
    this.itemList = [];
    this.wishListSubject.next(this.itemList);
    this.backendService.deleteWishList().subscribe();
    return this.wishListSubject;
  }

  cleaningUp(): void {
    this.itemList = [];
    this.wishListSubject.next(this.itemList);
  }

}

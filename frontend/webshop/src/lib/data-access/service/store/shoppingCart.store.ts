import {Injectable} from "@angular/core";
import {SpecifiedItem} from "../../models";
import {ReplaySubject} from "rxjs";
import {BackendService} from "../backend.service";

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartStore {
  // @ts-ignore
  itemList: SpecifiedItem[] = [];
  itemListSubject: ReplaySubject<SpecifiedItem[]> = new ReplaySubject<SpecifiedItem[]>(1);

  constructor(private backendService: BackendService) {

  }

  loadShoppingCart(): ReplaySubject<SpecifiedItem[]> {
    if (this.itemList.length == 0) {
      this.backendService.loadShoppingCart().subscribe(itemList => {
        this.itemList = itemList;
        this.itemListSubject.next(this.itemList);
      });
    } else {
      this.itemListSubject.next(this.itemList)
    }
    return this.itemListSubject;
  }

  updateItems(items: SpecifiedItem[]): ReplaySubject<SpecifiedItem[]> {
    this.itemList = items;
    this.itemListSubject.next(this.itemList);
    this.backendService.updateItemList(this.itemList).subscribe();
    return this.itemListSubject;
  }

  updateItem(itemId: number, newQuantity: number): ReplaySubject<SpecifiedItem[]> {
    let index = this.itemList.findIndex(i => itemId == i.id);
    this.itemList[index].quantity = newQuantity;
    this.backendService.updateShoppingCartItem(this.itemList[index]).subscribe(() =>
      this.backendService.loadShoppingCart().subscribe(items => {
        this.itemList = items;
        this.itemListSubject.next(this.itemList);
      })
    );
    return this.itemListSubject;
  }

  addItem(item: SpecifiedItem): ReplaySubject<SpecifiedItem[]> {
    this.backendService.addItemToShoppingCart(item).subscribe(() =>
      this.backendService.loadShoppingCart().subscribe(items => {
        this.itemList = items;
        this.itemListSubject.next(this.itemList);
      })
    );
    return this.itemListSubject;
  }

  deleteItem(itemId: number): ReplaySubject<any> {
    let index = this.itemList.findIndex(i => itemId == i.id);
    this.itemList.splice(index, 1);
    this.itemListSubject.next(this.itemList);
    this.backendService.deleteItem(itemId).subscribe();
    return this.itemListSubject;
  }

  cleaningUp(): void {
    this.itemList = [];
    this.itemListSubject.next(this.itemList);
  }
}

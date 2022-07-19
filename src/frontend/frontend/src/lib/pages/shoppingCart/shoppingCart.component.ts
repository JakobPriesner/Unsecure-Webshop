import {Component} from "@angular/core";
import {SpecifiedItem} from "src/lib/data-access/models";
import {ShoppingCartStore} from "../../data-access/service/store/shoppingCart.store";
import {BackendService} from "../../data-access/service/backend.service";
import {Router} from "@angular/router";

@Component({
  selector: 'shopping-cart',
  templateUrl: './shoppingCart.component.html',
  styleUrls: ['./shoppingCart.component.scss']
})
export class ShoppingCartComponent {

  itemList: SpecifiedItem[] | undefined;

  amount: number = 0;
  quantity: number = 0;

  constructor(private shoppingCartStore: ShoppingCartStore, private backendService: BackendService, private router: Router) {
  }

  onItemDelete(itemId: number): void {
    this.shoppingCartStore.deleteItem(itemId);
    this.calculateAmount();
  }

  onItemChange(event: any): void {
    this.shoppingCartStore.updateItem(event.itemId, event.quantity).subscribe(itemList => {
      this.itemList = itemList;
      this.calculateAmount();
    });
  }

  ngOnInit(): void {
    this.shoppingCartStore.loadShoppingCart().subscribe(itemList => {
      this.itemList = itemList;
      this.calculateAmount();
    });
  }

  calculateAmount(): void {
    this.amount = 0;
    this.quantity = 0;
    if (this.itemList) {
      this.itemList.forEach(element => {
        this.amount += element.amount ? element.amount * element.quantity : 0;
        this.amount = Math.round( this.amount * 100 ) / 100;
        this.quantity += element.quantity;
      });
    }
  }

  onNavigateToOrderProcess(): void {
    this.backendService.getLevel().subscribe( levelNumber => {
      if (levelNumber == 1)
      {
        this.router.navigate!(['/checkout'], {queryParams: {amount: this.amount}});
      } else {
         this.router.navigateByUrl!( "/checkout" );
      }
    } );
  }
}

import {Order} from "../../models";
import {ReplaySubject} from "rxjs";
import {BackendService} from "../backend.service";
import {Injectable} from "@angular/core";
import {ShoppingCartStore} from "./shoppingCart.store";

@Injectable({
  providedIn: 'root'
})
export class OrderStore {
  orders: Order[] = [];
  ordersSubject: ReplaySubject<Order[]> = new ReplaySubject<Order[]>(1);
  orderWithBody: Order[] = [];

  constructor(private backendService: BackendService, private shoppingCartStore: ShoppingCartStore) {
  }

  loadOrders(): ReplaySubject<Order[]> {
    if (this.orders.length == 0) {
      this.backendService.loadOrders().subscribe(orders => {
        this.orders = orders;
        this.ordersSubject.next(this.orders);
      });
    } else {
      this.ordersSubject.next(this.orders);
    }
    return this.ordersSubject;
  }

  loadOrderById(orderNumber: number): ReplaySubject<Order> {
    const orderLocalSubject: ReplaySubject<Order> = new ReplaySubject<Order>(1);
    let index = this.orderWithBody.findIndex(order => order.orderNumber === orderNumber);
    if (index == -1) {
      this.backendService.loadOrdersWithFullBody(orderNumber).subscribe(order => {
        this.orderWithBody.push(order);
        orderLocalSubject.next(order);
      });
    } else {
      orderLocalSubject.next(this.orderWithBody[index]);
    }
    return orderLocalSubject;
  }

  postOrder(order: Order, queryParamAmount: number): ReplaySubject<string> {
    const orderSubject: ReplaySubject<string> = new ReplaySubject<string>(1);
    if( queryParamAmount != -1 ) order.amount = queryParamAmount;
    this.backendService.postOrder(order).subscribe(order => {
      orderSubject.next(order);
      this.shoppingCartStore.itemList = [];
    })
    return orderSubject;
  }

  cleaningUp(): void {
    this.orders = [];
    this.ordersSubject.next(this.orders);
    this.orderWithBody = [];
  }
}

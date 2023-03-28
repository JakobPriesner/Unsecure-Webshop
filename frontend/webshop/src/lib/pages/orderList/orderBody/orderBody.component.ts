import {Component, Input, OnInit} from "@angular/core";
import {Order} from "../../../data-access/models";
import {OrderStore} from "../../../data-access/service/store/order.store";

@Component({
  selector: 'order-body',
  templateUrl: './orderBody.component.html',
  styleUrls: ['./orderBody.component.scss']
})
export class OrderBodyComponent implements OnInit {

  // @ts-ignore
  @Input() orderNumber: number;

  // @ts-ignore
  order: Order;

  newDate: string = '';

  constructor(private orderStore: OrderStore) {
  }

  ngOnInit() {
    this.orderStore.loadOrderById(this.orderNumber).subscribe(orderWithBody => {
      this.order = orderWithBody;
      this.updateDate();
    });
  }

  updateDate() {
    if (this.order.date) {
      let dateArray: string[] = this.order.date.split("-");
      let tmpDate: Date = new Date();
      tmpDate.setFullYear(parseInt(dateArray[0]), parseInt(dateArray[1]) + 1, parseInt(dateArray[2]));
      if (tmpDate.getMonth() != 0) {
        this.newDate = `${tmpDate.getDate()}.${tmpDate.getMonth().toString().padStart(2, "0")}.${tmpDate.getFullYear()}`
      } else {
        this.newDate = `${tmpDate.getDate()}.${12}.${tmpDate.getFullYear()}`
      }
    }
  }
}

import {Component, Input} from "@angular/core";
import {OrderStore} from "../../../data-access/service/store/order.store";
import {Order} from "../../../data-access/models";

@Component({
  selector: 'confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.scss']
})
export class ConfirmationComponent {

  // @ts-ignore
  @Input() order: Order;

  constructor(private orderStore: OrderStore) {
  }

}

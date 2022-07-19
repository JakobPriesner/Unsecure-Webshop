import {Component, OnInit} from '@angular/core';
import {Order} from "../../data-access/models";
import {OrderStore} from "../../data-access/service/store/order.store";
import {ImageStore} from "../../data-access/service/store/image.store";

@Component({
  // eslint-disable-next-line @angular-eslint/component-selector
  selector: 'order-list',
  templateUrl: './orderList.component.html',
  styleUrls: ['./orderList.component.scss']
})
export class OrderListComponent implements OnInit {
  orderList: Order[] = [];
  showBody: boolean = false;
  images: Map<number, string> = new Map<number, string>();

  constructor(private orderStore: OrderStore, private imageStore: ImageStore) {
  }

  ngOnInit() {
    this.orderStore.loadOrders().subscribe(orders => {
      this.orderList = orders;
    });
    this.orderList.forEach(order => {
        order.specifiedItems?.forEach(specifiedItem => {
          if (specifiedItem.pictureId) {
            this.imageStore.loadImageById(specifiedItem.pictureId).subscribe(image => {
              if (specifiedItem.pictureId) this.images.set(specifiedItem.pictureId, image);
            });
          }
        })
      }
    )
  }

}

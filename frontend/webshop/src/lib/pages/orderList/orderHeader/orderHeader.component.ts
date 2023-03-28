import {Component, Input, OnInit} from '@angular/core';
import {SpecifiedItem} from "../../../data-access/models";
import {ImageStore} from "../../../data-access/service/store/image.store";

@Component({
  // eslint-disable-next-line @angular-eslint/component-selector
  selector: 'order-header',
  templateUrl: './orderHeader.component.html',
  styleUrls: ['./orderHeader.component.scss']
})
export class OrderHeaderComponent implements OnInit {
  // @ts-ignore
  @Input() itemList: SpecifiedItem[];
  // @ts-ignore
  @Input() orderDate: string;

  // @ts-ignore
  images: Map<number, string> = new Map<number, string>();

  isOpen: boolean = false;

  constructor(private imageStore: ImageStore) {
  }

  ngOnInit() {
    if (this.itemList) {
      this.itemList.forEach(item => {
        if (item.pictureId) {
          this.imageStore.loadImageById(item.pictureId).subscribe(image => {
            if (item.pictureId) {
              this.images.set(item.pictureId, image);
            }
          });
        }
      });
    }
  }

  toggleIsOpen() {
    this.isOpen = !this.isOpen;
  }


}

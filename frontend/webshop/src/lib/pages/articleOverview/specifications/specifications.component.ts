import {Component, EventEmitter, Input, Output} from "@angular/core";
import {Article} from "../../../data-access/models";

@Component({
  selector: 'article-overview-specifications',
  templateUrl: './specifications.component.html',
  styleUrls: ['./specifications.component.scss']
})
export class SpecificationsComponent {
  // @ts-ignore
  @Input article: Article;
  @Output() colorChangedEvent: EventEmitter<string> = new EventEmitter<string>();
  @Output() storageChangeEvent: EventEmitter<number> = new EventEmitter<number>();

  onColorChange(value: string): void {
    this.colorChangedEvent.emit(value);
  }

  onStorageChange(value: number): void {
    this.storageChangeEvent.emit(value);
  }

}

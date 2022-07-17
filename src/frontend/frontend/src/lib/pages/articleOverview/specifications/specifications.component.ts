import {Component, EventEmitter, Input, Output} from "@angular/core";
import {Article, Commentary} from "../../../data-access/models";

@Component({
  selector: 'article-overview-specifications',
  templateUrl: './specifications.component.html',
  styleUrls: ['./specifications.component.scss']
})
export class SpecificationsComponent {
  @Input() article: Article | undefined;
  @Output() colorChangedEvent: EventEmitter<string> = new EventEmitter<string>();
  @Output() storageChangeEvent: EventEmitter<number> = new EventEmitter<number>();

  onColorChange(value: string): void {
    this.colorChangedEvent.emit(value);
  }

  onStorageChange(value: number): void {
    this.storageChangeEvent.emit(value);
  }

  getArticleBrand(): string {
    return this.article?.brand ?? "unbekannt";
  }

  getArticleStars(): number {
    return this.article?.stars ?? -1;
  }

  getArticleScreen(): string {
    return this.article?.screen ?? "unbekannt";
  }

  getArticleResolution(): string {
    return this.article?.resolution ?? "unbekannt";
  }

  getArticleComments(): Commentary[] {
    return this.article?.comments ?? [];
  }
}

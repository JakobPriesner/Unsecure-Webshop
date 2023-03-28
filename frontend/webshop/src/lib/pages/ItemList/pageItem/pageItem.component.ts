import {Component, Input, OnInit} from '@angular/core';
import {Article} from "../../../data-access/models";
import {ImageStore} from "../../../data-access/service/store/image.store";

@Component({
  selector: 'page-item',
  templateUrl: './pageItem.component.html',
  styleUrls: ['./pageItem.component.scss']
})
export class PageItemComponent implements OnInit {
  // @ts-ignore
  @Input() article: Article;

  // @ts-ignore
  image: string;

  constructor(private imageStore: ImageStore) {
  }


  ngOnInit(): void {
    this.imageStore.loadImageById(this.article.articleNumber).subscribe(image => this.image = image);
  }


}

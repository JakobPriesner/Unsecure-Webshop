import {Component, Input, OnInit} from "@angular/core";
import {Article} from "../../../data-access/models";
import {ImageStore} from "../../../data-access/service/store/image.store";

@Component({
  selector: 'front-item',
  templateUrl: './frontItem.component.html',
  styleUrls: ['./frontItem.component.scss']
})

export class FrontItemComponent implements OnInit{
  // @ts-ignore
  @Input() article: Article;

  image: string = "";

  constructor(private imageStore: ImageStore) {
  }

  ngOnInit() {
    if(this.article.pictureIds){
        this.imageStore.loadImageById(this.article.pictureIds[0]).subscribe(image => {
          this.image = image;
        });
    }

  }
}

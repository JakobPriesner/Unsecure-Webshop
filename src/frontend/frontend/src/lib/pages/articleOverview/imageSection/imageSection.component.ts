import {Component, Input, OnInit} from "@angular/core";
import {Article} from "../../../data-access/models";
import {ImageStore} from "../../../data-access/service/store/image.store";

@Component({
  selector: 'article-overview-images',
  templateUrl: './imageSection.component.html',
  styleUrls: ['./imageSection.component.scss']
})
export class ImageSectionComponent implements OnInit {
  @Input() article: Article | undefined;

  images: Map<number, string> = new Map<number, string>();

  constructor(private imageStore: ImageStore) {
  }

  ngOnInit() {
    this.article?.pictureIds?.forEach(id => {
      this.imageStore.loadImageById(id).subscribe(image => {
        this.images.set(id, image);
      });
    });
  }
}

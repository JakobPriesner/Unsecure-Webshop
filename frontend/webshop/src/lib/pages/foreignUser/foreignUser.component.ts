import {Component, OnInit} from "@angular/core";
import {DomSanitizer} from "@angular/platform-browser";
import {ForeignUserStore} from "../../data-access/service/store/foreignUser.store";
import {ActivatedRoute} from "@angular/router";
import {User} from "../../data-access/models";

@Component({
  selector: 'foreign-user',
  templateUrl: './foreignUser.component.html',
  styleUrls: ['./foreignUser.component.scss']
})
export class ForeignUserComponent implements OnInit {

  // @ts-ignore
  user: User;

  constructor(private foreignUserStore: ForeignUserStore, private route: ActivatedRoute, private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    let userId: number = this.route.snapshot.params['id'];
    this.foreignUserStore.loadUserById(userId).subscribe(user => {
      this.user = user;
    })
  }


}

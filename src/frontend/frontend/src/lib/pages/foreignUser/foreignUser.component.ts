import {Component, OnInit} from "@angular/core";
import {ForeignUserStore} from "../../data-access/service/store/foreignUser.store";
import {ActivatedRoute} from "@angular/router";
import {User} from "../../data-access/models";

@Component({
  selector: 'foreign-user',
  templateUrl: './foreignUser.component.html',
  styleUrls: ['./foreignUser.component.scss']
})
export class ForeignUserComponent implements OnInit {

  user: User | undefined = undefined;

  constructor(private foreignUserStore: ForeignUserStore,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    let userId: number = this.route.snapshot.params['id'];
    this.foreignUserStore.loadUserById(userId).subscribe(user => {
      this.user = user;
    })
  }


}

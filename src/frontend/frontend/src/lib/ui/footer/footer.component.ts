import {Component, OnInit, TemplateRef} from "@angular/core";
import {AuthenticationService} from "../../data-access/service/authentication.service";
import {UuidService} from "../../data-access/service/uuidService";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";

@Component({
  selector: 'footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})

export class FooterComponent implements OnInit {

  login: boolean = false;
  uuid: String = "";
  modalRef?: BsModalRef;

  constructor(private authenticationService: AuthenticationService, private uuidService: UuidService, private modalService: BsModalService) {
  }

  ngOnInit() {
    this.authenticationService.getStatus().subscribe(
      status => {
        this.login = status != -1;
      }
    );
    this.uuidService.uuid.subscribe(uuid => this.uuid = uuid);
  }

  openUuidModal(template: TemplateRef<any>): void {
    this.modalRef = this.modalService.show(template);
  }
}

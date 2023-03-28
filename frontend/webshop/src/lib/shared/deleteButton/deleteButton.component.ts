import {Component, EventEmitter, Input, Output} from "@angular/core";

@Component({
  selector: 'delete',
  templateUrl: './deleteButton.component.html',
  styleUrls: ['./deleteButton.component.scss']
})
export class DeleteButtonComponent {
  @Output() onClickEvent: EventEmitter<string> = new EventEmitter<string>();

  onClick( ): void {
    this.onClickEvent.emit( );
  }

}

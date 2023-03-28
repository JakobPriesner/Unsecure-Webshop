import {Component, EventEmitter, Input, Output} from "@angular/core";

@Component({
  selector: 'color-circle',
  templateUrl: './colorCircle.component.html',
  styleUrls: ['./colorCircle.component.scss']
})
export class ColorCircleComponent {
  @Input() color: string = "";
  @Input() clickable: boolean = false;

  @Output() onClickEvent: EventEmitter<string> = new EventEmitter<string>();

  onClick( ): void {
    this.onClickEvent.emit( this.color );
  }

}

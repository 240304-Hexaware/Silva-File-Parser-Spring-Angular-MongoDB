import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ParsedFile, SpecFile, User } from '../../../types';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-new-parsed-file-view',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './new-parsed-file-view.component.html',
  styleUrl: './new-parsed-file-view.component.scss',
})
export class NewParsedFileViewComponent {
  @Input() currUser!: User;
  @Input() listOfParsedFiles!: ParsedFile[];
  @Input() specFile!: SpecFile | null;

  @Output() displayChange: EventEmitter<boolean> = new EventEmitter<boolean>();

  onClose(): void {
    this.displayChange.emit(false);
  }
}

import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DialogModule } from 'primeng/dialog';

@Component({
  selector: 'app-add-spec-file-popup',
  standalone: true,
  imports: [DialogModule],
  templateUrl: './add-spec-file-popup.component.html',
  styleUrl: './add-spec-file-popup.component.scss',
})
export class AddSpecFilePopupComponent {
  @Input() display: boolean = false;
  @Output() displayChange: EventEmitter<boolean> = new EventEmitter<boolean>();

  @Input() file: File | null = null;
  @Output() fileChange: EventEmitter<File> = new EventEmitter<File>();

  onConfirm() {
    if (this.file) {
      this.fileChange.emit(this.file);
    } else {
      //TODO: No file Uploaded Error
    }
    this.display = false;
    this.displayChange.emit(this.display);
  }

  onCancel() {
    this.display = false;
    this.displayChange.emit(this.display);
  }

  // Bind Uploaded file to private file
  //TODO: Find better type for event
  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.file = file;
    }
  }
}

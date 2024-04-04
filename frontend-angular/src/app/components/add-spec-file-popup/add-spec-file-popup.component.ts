import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DialogModule } from 'primeng/dialog';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { User } from '../../../types';
import { SpecFileViewService } from '../services/spec-file-view.service';

@Component({
  selector: 'app-add-spec-file-popup',
  standalone: true,
  imports: [DialogModule, CommonModule, ToastModule],
  templateUrl: './add-spec-file-popup.component.html',
  styleUrl: './add-spec-file-popup.component.scss',
  providers: [MessageService],
})
export class AddSpecFilePopupComponent {
  @Input() currUser!: User;
  isInvalidFileType: boolean = false;
  isConfirmDisabled: boolean = true;
  @Input() display: boolean = false;
  @Output() displayChange: EventEmitter<boolean> = new EventEmitter<boolean>();

  file: File = new File(['Invalid File'], 'invalid.txt', {
    type: 'text/plain',
  });

  constructor(
    private specFileViewService: SpecFileViewService,
    private messageService: MessageService
  ) {}

  onConfirm() {
    this.specFileViewService.postSpecFile(this.file, this.currUser).subscribe({
      next: (data) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Added Specfile',
          detail: `${this.file.name}`,
        });
      },
      error: (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Could not Add Spec File',
          detail: error.error,
        });
      },
    });

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
    console.log(event);
    const file: File = event.target.files[0];
    if (file.type == 'application/json') {
      this.isInvalidFileType = false;
      this.isConfirmDisabled = false;
      this.file = file;
    } else {
      this.isInvalidFileType = true;
      this.isConfirmDisabled = true;
    }
  }
}

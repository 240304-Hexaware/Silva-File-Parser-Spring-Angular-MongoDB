import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { EventFlatAndSpec, SpecFile, User } from '../../../types';
import { FormsModule, NgModel } from '@angular/forms';
import { SpecFileViewService } from '../services/spec-file-view.service';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-add-parsed-file-popup',
  standalone: true,
  imports: [DialogModule, DropdownModule, FormsModule, CommonModule],
  templateUrl: './add-parsed-file-popup.component.html',
  styleUrl: './add-parsed-file-popup.component.scss',
})
export class AddParsedFilePopupComponent {
  @Input() display: boolean = false;
  @Output() displayChange: EventEmitter<boolean> = new EventEmitter<boolean>();
  //TODO: Find Better solution than !
  file!: File;
  specFile!: SpecFile;
  listOfSpecFiles: SpecFile[] = [];
  hasParsedFileSelected: boolean = false;
  hasSpecFileSelected: boolean = false;
  isInvalidFileType: boolean = false;

  @Input() currUser!: User;
  @Output() fileChange: EventEmitter<EventFlatAndSpec> =
    new EventEmitter<EventFlatAndSpec>();

  constructor(private specFileViewService: SpecFileViewService) {}
  onConfirm() {
    if (this.file) {
      this.fileChange.emit({ file: this.file, specFile: this.specFile });
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

  //TODO: Find better type for event
  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file.type == 'text/plain') {
      this.hasParsedFileSelected = true;
      this.isInvalidFileType = false;
      this.file = file;
      console.log(this.hasParsedFileSelected);
    } else {
      this.hasParsedFileSelected = false;
      this.isInvalidFileType = true;
    }
  }

  onDropDownClick(): void {
    this.specFileViewService
      .fetchAllSpecFilesOfUser(this.currUser)
      .subscribe((data: SpecFile[]) => {
        this.listOfSpecFiles = data;
      });
  }

  onDropDownChange(): void {
    this.hasSpecFileSelected = true;
  }

  onDropDownClear(): void {
    this.hasSpecFileSelected = false;
  }
}

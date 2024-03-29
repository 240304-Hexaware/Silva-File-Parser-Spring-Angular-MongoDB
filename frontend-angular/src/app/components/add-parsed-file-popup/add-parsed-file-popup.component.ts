import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { EventFlatAndSpec, SpecFile, User } from '../../../types';
import { FormsModule } from '@angular/forms';
import { SpecFileViewService } from '../services/spec-file-view.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-add-parsed-file-popup',
  standalone: true,
  imports: [DialogModule, DropdownModule, FormsModule],
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
    if (file) {
      this.file = file;
    }
  }

  onDropDownClick(): void {
    this.specFileViewService.fetchAllSpecFilesOfUser(this.currUser);
    this.listOfSpecFiles = this.specFileViewService.getListOfSpecFiles();
  }

  //TODO: Find a way to check using other than ngDoCheck
  ngDoCheck() {
    // Intialize listOfSpecFiles
    this.listOfSpecFiles = this.specFileViewService.getListOfSpecFiles();
  }
}

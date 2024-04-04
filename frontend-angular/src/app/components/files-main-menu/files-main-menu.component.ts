import { Component, EventEmitter, Input, Output } from '@angular/core';
import { EventFlatAndSpec, SpecFile, User } from '../../../types';
import { DialogModule } from 'primeng/dialog';
import { AddSpecFilePopupComponent } from '../add-spec-file-popup/add-spec-file-popup.component';
import { AddParsedFilePopupComponent } from '../add-parsed-file-popup/add-parsed-file-popup.component';

@Component({
  selector: 'app-files-main-menu',
  standalone: true,
  imports: [
    DialogModule,
    AddSpecFilePopupComponent,
    AddParsedFilePopupComponent,
  ],
  templateUrl: './files-main-menu.component.html',
  styleUrl: './files-main-menu.component.scss',
})
export class FilesMainMenuComponent {
  @Input() currUser!: User;

  //FilesMainMenu Component displays Popups (This Class)
  displayAddSpecFilePopup: boolean = false;
  displayAddParsedFilePopup: boolean = false;

  //Home Component Displays Parsed Files
  @Output() displayParsedFiles: EventEmitter<boolean> =
    new EventEmitter<boolean>();
  @Output()
  parsedFileChange: EventEmitter<EventFlatAndSpec> =
    new EventEmitter<EventFlatAndSpec>();

  onViewFiles(): void {
    this.displayParsedFiles.emit(true);
  }

  onParseFiles(): void {
    this.displayAddParsedFilePopup = true;
  }

  onCreateSpecFile(): void {
    this.displayAddSpecFilePopup = true;
  }

  onParseFileChange(event: EventFlatAndSpec): void {
    // Emit Event containing Flat File and SpecFile up to Home Component
    this.parsedFileChange.emit(event);
  }
}

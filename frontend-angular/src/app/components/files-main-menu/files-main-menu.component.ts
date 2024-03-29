import { Component, EventEmitter, Input, Output } from '@angular/core';
import { EventFlatAndSpec, SpecFile, User } from '../../../types';
import { ParsedFileViewService } from '../services/parsed-file-view.service';
import { DialogModule } from 'primeng/dialog';
import { AddSpecFilePopupComponent } from '../add-spec-file-popup/add-spec-file-popup.component';
import { AddParsedFilePopupComponent } from '../add-parsed-file-popup/add-parsed-file-popup.component';
import { SpecFileViewService } from '../services/spec-file-view.service';

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

  constructor(
    private parsedFileViewService: ParsedFileViewService,
    private specFileViewService: SpecFileViewService
  ) {}

  /**
   * When the View Files button is clicked.
   * Fetch all the Files.
   * Then emit that the parsed files should be displayed
   */
  onViewFiles(): void {
    this.parsedFileViewService.fetchAllParsedFilesOfUser(this.currUser);
    this.specFileViewService.fetchAllSpecFilesOfUser(this.currUser);
    this.displayParsedFiles.emit(true);
  }

  /**
   * When Parse File Button is clicked
   * Fetch All SpecFiles of the User, so it can be sent to AddSpecFilePopup.
   * Also set display to true for popup
   */
  onParseFiles(): void {
    this.specFileViewService.fetchAllSpecFilesOfUser(this.currUser);
    this.displayAddParsedFilePopup = true;
  }

  onCreateSpecFile(): void {
    this.displayAddSpecFilePopup = true;
  }

  onParseFileChange(event: EventFlatAndSpec): void {
    this.parsedFileViewService.postParsedFile(event, this.currUser);
    //TODO: Find a way to refresh Parsed View Files after post
  }

  onSpecFileChange(event: File): void {
    this.specFileViewService.postSpecFile(event, this.currUser);
  }
}

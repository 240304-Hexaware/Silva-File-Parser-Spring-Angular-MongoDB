import { Component, EventEmitter, Input, Output } from '@angular/core';
import { EventFlatAndSpec, ParsedFile, SpecFile, User } from '../../../types';
import { ParsedFilesService } from '../../services/parsed-files.service';
import { ParsedFileViewService } from '../services/parsed-file-view.service';
import { DialogModule } from 'primeng/dialog';
import { AddSpecFilePopupComponent } from '../add-spec-file-popup/add-spec-file-popup.component';
import { SpecFileApiService } from '../../services/spec-file-api.service';
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
  //TODO: Consider removing this variable
  listOfParsedFiles: ParsedFile[] = [];
  listOfSpecFiles: SpecFile[] = [];
  specFileForParsing: SpecFile | null = null;

  displayAddSpecFilePopup: boolean = false;
  displayAddParsedFilePopup: boolean = false;

  @Output() displayParsedFiles: EventEmitter<boolean> =
    new EventEmitter<boolean>();

  constructor(
    private parsedFilesService: ParsedFilesService,
    private parsedFileViewService: ParsedFileViewService,
    private specFileApiService: SpecFileApiService
  ) {}

  onViewFiles(): void {
    this.fetchAllParsedFilesOfUser();
    this.displayParsedFiles.emit(true);
  }

  onParseFiles(): void {
    console.log('Parse File Button clicked');
    this.fetchAllSpecFilesOfUser();
    this.displayAddParsedFilePopup = true;
  }

  onCreateSpecFile(): void {
    this.displayAddSpecFilePopup = true;
  }

  specFileChange(event: File): void {
    const formData = new FormData();
    formData.append('specFileAsJson', event, event.name);
    this.specFileApiService.postSpecFile(this.currUser, formData).subscribe({});
  }

  parseFileChange(event: EventFlatAndSpec): void {
    const formData = new FormData();
    formData.append('flatFile', event.file, event.file.name);
    formData.append('flatFileName', event.specFile.name);
    this.parsedFilesService
      .postParsedFile(this.currUser, event.specFile, formData)
      .subscribe({});
    //TODO: Find a way to refresh View Files after posting
    this.onViewFiles();
  }

  fetchAllParsedFilesOfUser(): void {
    this.parsedFilesService
      .getAllFilesByUser(this.currUser)
      .subscribe((data: ParsedFile[]) => {
        this.listOfParsedFiles = data;
        this.parsedFileViewService.setListOfParsedFiles(this.listOfParsedFiles);
      });
  }

  fetchAllSpecFilesOfUser(): void {
    this.specFileApiService
      .getAllFilesByUser(this.currUser)
      .subscribe((data: SpecFile[]) => {
        this.listOfSpecFiles = data;
      });
  }
}

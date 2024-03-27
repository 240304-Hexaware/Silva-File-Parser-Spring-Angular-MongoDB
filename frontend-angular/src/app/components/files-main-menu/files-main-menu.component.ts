import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ParsedFile, User } from '../../../types';
import { ParsedFilesService } from '../../services/parsed-files.service';
import { ParsedFileViewService } from '../services/parsed-file-view.service';
import { DialogModule } from 'primeng/dialog';
import { AddSpecFilePopupComponent } from '../add-spec-file-popup/add-spec-file-popup.component';
import { SpecFileApiService } from '../../services/spec-file-api.service';

@Component({
  selector: 'app-files-main-menu',
  standalone: true,
  imports: [DialogModule, AddSpecFilePopupComponent],
  templateUrl: './files-main-menu.component.html',
  styleUrl: './files-main-menu.component.scss',
})
export class FilesMainMenuComponent {
  @Input() currUser!: User;
  //TODO: Consider removing this variable
  listOfParsedFiles: ParsedFile[] = [];
  displayAddSpecFilePopup: boolean = false;

  @Output() displayParsedFiles: EventEmitter<boolean> =
    new EventEmitter<boolean>();

  constructor(
    private parsedFilesService: ParsedFilesService,
    private parsedFileViewService: ParsedFileViewService,
    private specFileApiService: SpecFileApiService
  ) {}

  onViewFiles(): void {
    this.fetchAllFilesOfUser();
    this.displayParsedFiles.emit(true);
  }

  onParseFiles(): void {
    console.log('Parse File Button clicked');
  }

  onCreateSpecFile(): void {
    console.log('Add Spec File button clicked');
    this.displayAddSpecFilePopup = true;
  }

  fileChange(event: File): void {
    const formData = new FormData();
    formData.append('specFileAsJson', event, event.name);
    console.log(event);
    console.log(formData);
    this.specFileApiService.postSpecFile(this.currUser, formData).subscribe({});
  }

  fetchAllFilesOfUser(): void {
    this.parsedFilesService
      .getAllFilesByUser(this.currUser)
      .subscribe((data: ParsedFile[]) => {
        this.listOfParsedFiles = data;
        this.parsedFileViewService.setListOfParsedFiles(this.listOfParsedFiles);
      });
  }
}

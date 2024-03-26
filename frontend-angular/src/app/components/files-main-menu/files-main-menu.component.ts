import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ParsedFile, User } from '../../../types';
import { ParsedFilesService } from '../../services/parsed-files.service';
import { ParsedFileViewService } from '../services/parsed-file-view.service';

@Component({
  selector: 'app-files-main-menu',
  standalone: true,
  imports: [],
  templateUrl: './files-main-menu.component.html',
  styleUrl: './files-main-menu.component.scss',
})
export class FilesMainMenuComponent {
  @Input() currUser!: User;
  //TODO: Consider removing this variable
  listOfParsedFiles: ParsedFile[] = [];
  @Output() displayParsedFiles: EventEmitter<boolean> =
    new EventEmitter<boolean>();

  constructor(
    private parsedFilesService: ParsedFilesService,
    private parsedFileViewService: ParsedFileViewService
  ) {}

  onViewFiles(): void {
    this.fetchAllFilesOfUser();
    this.displayParsedFiles.emit(true);
  }

  onParseFiles(): void {
    console.log('Parse File Button clicked');
  }

  onCreateSpecFile(): void {
    console.log('Create new spec file button pressed');
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

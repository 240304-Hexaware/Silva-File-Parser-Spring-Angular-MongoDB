import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ParsedFile, SpecFile, User } from '../../../types';
import { ParsedFileViewService } from '../services/parsed-file-view.service';
import { CommonModule } from '@angular/common';
import { SpecFileViewService } from '../services/spec-file-view.service';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { SpecFileApiService } from '../../services/spec-file-api.service';
import { ParsedFilesService } from '../../services/parsed-files.service';

@Component({
  selector: 'app-parsed-file-view',
  standalone: true,
  imports: [CommonModule, DropdownModule, FormsModule],
  templateUrl: './parsed-file-view.component.html',
  styleUrl: './parsed-file-view.component.scss',
})
export class ParsedFileViewComponent {
  listOfParsedFiles: ParsedFile[] = [];
  listOfSpecFiles: SpecFile[] = [];
  specFile!: SpecFile;
  @Input() currUser!: User;
  @Output() displayParsedFiles: EventEmitter<boolean> =
    new EventEmitter<boolean>();
  @Output() specFileChange: EventEmitter<SpecFile> =
    new EventEmitter<SpecFile>();

  constructor(
    private parsedFileViewService: ParsedFileViewService,
    private specFileViewService: SpecFileViewService,
    private parseFilesService: ParsedFilesService
  ) {}

  onClose(): void {
    this.displayParsedFiles.emit(false);
  }

  onChooseSpecFile(): void {
    this.fetchAllParseFilesOfUserBySpecId();
    this.specFileChange.emit(this.specFile);
  }

  // TODO: Understand why ngOnInit does not work here
  // Responds after Angular checks the content projected into the directive
  ngDoCheck(): void {
    this.listOfParsedFiles = this.parsedFileViewService.getListOfParsedFiles();
    this.listOfSpecFiles = this.specFileViewService.getListOfSpecFiles();
  }

  fetchAllParseFilesOfUserBySpecId(): void {
    this.parseFilesService
      .getAllFilesOfUserBySpecId(this.currUser, this.specFile)
      .subscribe((data: ParsedFile[]) => {
        this.listOfParsedFiles = data;
        this.parsedFileViewService.setListOfParsedFiles(this.listOfParsedFiles);
      });
  }
}

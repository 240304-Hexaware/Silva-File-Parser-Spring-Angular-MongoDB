import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ParsedFile, SpecFile, User } from '../../../types';
import { ParsedFileViewService } from '../services/parsed-file-view.service';
import { CommonModule } from '@angular/common';
import { SpecFileViewService } from '../services/spec-file-view.service';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';

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
    private specFileViewService: SpecFileViewService
  ) {}

  onClose(): void {
    this.displayParsedFiles.emit(false);
  }

  onChooseSpecFile(): void {
    this.parsedFileViewService.fetchAllParseFilesOfUserBySpecId(
      this.currUser,
      this.specFile
    );
  }

  // ngOnInit(): void {
  //   this.listOfSpecFiles = this.specFileViewService.getListOfSpecFiles();
  // }
  // TODO: Understand why ngOnInit does not work here
  // Responds after Angular checks the content projected into the directive
  ngDoCheck(): void {
    this.listOfParsedFiles = this.parsedFileViewService.getListOfParsedFiles();
    this.listOfSpecFiles = this.specFileViewService.getListOfSpecFiles();
  }
}

import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ParsedFile, User } from '../../../types';
import { ParsedFileViewService } from '../services/parsed-file-view.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-parsed-file-view',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './parsed-file-view.component.html',
  styleUrl: './parsed-file-view.component.scss',
})
export class ParsedFileViewComponent {
  listOfParsedFiles: ParsedFile[] = [];
  @Input() currUser!: User;
  @Output() displayParsedFiles: EventEmitter<boolean> =
    new EventEmitter<boolean>();

  constructor(private parsedFileViewService: ParsedFileViewService) {}

  onClose(): void {
    this.displayParsedFiles.emit(false);
  }

  // TODO: Understand why ngOnInit does not work here
  // Responds after Angular checks the content projected into the directive
  ngDoCheck(): void {
    this.listOfParsedFiles = this.parsedFileViewService.getListOfParsedFiles();
  }
}

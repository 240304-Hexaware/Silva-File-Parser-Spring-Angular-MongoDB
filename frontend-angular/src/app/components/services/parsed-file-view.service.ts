import { Injectable } from '@angular/core';
import { ParsedFile } from '../../../types';

@Injectable({
  providedIn: 'root',
})
export class ParsedFileViewService {
  listOfParsedFiles: ParsedFile[] = [];

  constructor() {}

  getListOfParsedFiles(): ParsedFile[] {
    return this.listOfParsedFiles;
  }

  setListOfParsedFiles(newList: ParsedFile[]): void {
    this.listOfParsedFiles = newList;
  }
}

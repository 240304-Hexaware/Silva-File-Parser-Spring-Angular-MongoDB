import { Injectable } from '@angular/core';
import { ParsedFile } from '../../../types';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ParsedFileViewService {
  listOfParsedFiles: BehaviorSubject<ParsedFile[]> = new BehaviorSubject<
    ParsedFile[]
  >([]);

  constructor() {}

  // getListOfParsedFiles(): ParsedFile[] {
  //   return this.listOfParsedFiles;
  // }

  setListOfParsedFiles(newList: ParsedFile[]): void {
    this.listOfParsedFiles.next(newList);
  }
}

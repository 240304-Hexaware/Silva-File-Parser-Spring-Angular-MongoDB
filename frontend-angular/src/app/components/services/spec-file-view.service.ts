import { Injectable } from '@angular/core';
import { SpecFile } from '../../../types';

@Injectable({
  providedIn: 'root',
})
export class SpecFileViewService {
  listOfSpecFiles: SpecFile[] = [];
  constructor() {}

  getListOfSpecFiles(): SpecFile[] {
    return this.listOfSpecFiles;
  }

  setListOfSpecFiles(newList: SpecFile[]): void {
    this.listOfSpecFiles = newList;
  }
}

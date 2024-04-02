import { Injectable } from '@angular/core';
import { EventFlatAndSpec, ParsedFile, SpecFile, User } from '../../../types';
import { ParsedFilesService } from '../../services/parsed-files.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ParsedFileViewService {
  listOfParsedFiles: ParsedFile[] = [];

  constructor(private parsedFilesService: ParsedFilesService) {}

  getListOfParsedFiles(): ParsedFile[] {
    return this.listOfParsedFiles;
  }

  setListOfParsedFiles(newList: ParsedFile[]): void {
    this.listOfParsedFiles = newList;
  }

  /**
   * Fetch All the Parsed file of the current User.
   * Set the list of acquired files tot he parsedFileViewService which stores the data.
   */
  fetchAllParsedFilesOfUser(user: User): Observable<ParsedFile[]> {
    return this.parsedFilesService.getAllFilesByUser(user);
    // .subscribe((data: ParsedFile[]) => {
    //   this.setListOfParsedFiles(data);
    // });
  }

  fetchAllParseFilesOfUserBySpecId(
    user: User,
    specFile: SpecFile
  ): Observable<ParsedFile[]> {
    return this.parsedFilesService.getAllFilesOfUserBySpecId(user, specFile);
    // .subscribe((data: ParsedFile[]) => {
    //   this.setListOfParsedFiles(data);
    // });
  }

  postParsedFile(event: EventFlatAndSpec, user: User): void {
    const formData = new FormData();
    formData.append('flatFile', event.file, event.file.name);
    formData.append('flatFileName', event.file.name);
    this.parsedFilesService
      .postParsedFile(user, event.specFile, formData)
      .subscribe((data: any) => {
        for (let ps of data) {
          this.listOfParsedFiles.push(ps);
        }
      });
    //TODO: Find a way to refresh Parsed View Files after posting
  }
}

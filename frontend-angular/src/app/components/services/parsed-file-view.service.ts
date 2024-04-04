import { Injectable, Input } from '@angular/core';
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
  }

  fetchAllParseFilesOfUserBySpecId(
    user: User,
    specFile: SpecFile
  ): Observable<ParsedFile[]> {
    return this.parsedFilesService.getAllFilesOfUserBySpecId(user, specFile);
  }

  postParsedFile(
    event: EventFlatAndSpec,
    user: User
  ): Observable<ParsedFile[]> {
    const formData = new FormData();
    formData.append('flatFile', event.file, event.file.name);
    formData.append('flatFileName', event.file.name);
    return this.parsedFilesService.postParsedFile(
      user,
      event.specFile,
      formData
    );
  }
}

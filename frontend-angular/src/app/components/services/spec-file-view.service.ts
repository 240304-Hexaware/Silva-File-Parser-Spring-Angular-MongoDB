import { Injectable } from '@angular/core';
import { SpecFile, User } from '../../../types';
import { SpecFileApiService } from '../../services/spec-file-api.service';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SpecFileViewService {
  listOfSpecFiles: SpecFile[] = [];
  constructor(private specFileApiService: SpecFileApiService) {}

  getListOfSpecFiles(): SpecFile[] {
    return this.listOfSpecFiles;
  }

  setListOfSpecFiles(newList: SpecFile[]): void {
    this.listOfSpecFiles = newList;
  }

  fetchAllSpecFilesOfUser(user: User): Observable<SpecFile[]> {
    return this.specFileApiService.getAllFilesByUser(user);
  }

  // TODO: Replace any
  postSpecFile(event: File, user: User): Observable<SpecFile[]> {
    const formData = new FormData();
    formData.append('specFileAsJson', event, event.name);
    return this.specFileApiService.postSpecFile(user, formData);
  }
}

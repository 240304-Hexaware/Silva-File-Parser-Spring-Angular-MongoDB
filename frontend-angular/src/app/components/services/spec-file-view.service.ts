import { Injectable } from '@angular/core';
import { SpecFile, User } from '../../../types';
import { SpecFileApiService } from '../../services/spec-file-api.service';

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

  fetchAllSpecFilesOfUser(user: User): void {
    this.specFileApiService
      .getAllFilesByUser(user)
      .subscribe((data: SpecFile[]) => {
        this.setListOfSpecFiles(data);
      });
  }

  postSpecFile(event: File, user: User) {
    const formData = new FormData();
    formData.append('specFileAsJson', event, event.name);
    this.specFileApiService.postSpecFile(user, formData).subscribe({});
  }
}

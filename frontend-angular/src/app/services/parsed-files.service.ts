import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { ParsedFile, SpecFile, User } from '../../types';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ParsedFilesService {
  constructor(private apiService: ApiService) {}
  username: string = 'InvalidUser';

  //Update Dymically what the username is.
  baseURL = (): string => `http://localhost:8080/users/${this.username}/file`;

  getAllFilesByUser = (user: User): Observable<ParsedFile[]> => {
    this.username = user.username;
    return this.apiService.get(this.baseURL(), { responseType: 'json' });
  };

  getAllFilesOfUserBySpecId = (
    user: User,
    specFile: SpecFile
  ): Observable<ParsedFile[]> => {
    this.username = user.username;
    return this.apiService.get(`${this.baseURL()}/specFile/${specFile.name}`, {
      responseType: 'json',
    });
  };

  postParsedFile = (
    user: User,
    specFile: SpecFile,
    formData: FormData
  ): Observable<ParsedFile> => {
    this.username = user.username;
    return this.apiService.post(
      `${this.baseURL()}/specFile/${specFile.name}`,
      formData,
      {}
    );
  };
}

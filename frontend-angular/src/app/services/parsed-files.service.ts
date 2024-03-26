import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { ParsedFile, User } from '../../types';
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
    console.log(this.baseURL());
    return this.apiService.get(this.baseURL(), { responseType: 'json' });
  };
}

import { Injectable } from '@angular/core';
import { SpecFile, User } from '../../types';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root',
})
export class SpecFileApiService {
  constructor(private apiService: ApiService) {}
  username: string = 'InvalidUser';

  //Update Dymically what the username is.
  baseURL = (): string =>
    `http://localhost:8080/users/${this.username}/specFile/`;

  postSpecFile = (user: User, formData: FormData): Observable<any> => {
    this.username = user.username;
    return this.apiService.post(this.baseURL(), formData, {});
  };
}

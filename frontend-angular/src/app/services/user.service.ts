import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable } from 'rxjs';
import { User } from '../../types';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private apiService: ApiService) {}
  readonly baseUrl = 'http://localhost:8080/users/';

  getUserByUsername = (url: string): Observable<User> => {
    return this.apiService.get(this.baseUrl + 'username/' + url, {
      responseType: 'json',
    });
  };
}

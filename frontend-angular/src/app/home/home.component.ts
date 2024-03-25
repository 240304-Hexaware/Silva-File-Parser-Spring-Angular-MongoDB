import { Component } from '@angular/core';
import { UserService } from '../services/user.service';
import { User } from '../../types';
import { error } from 'console';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  constructor(private userService: UserService) {}

  currUser: User = {
    id: 0,
    username: 'InvalidUser',
    password: 'BadPassword',
    listOfSpecFileIds: [],
    listOfParsedFileIds: [],
  };

  fetchUserByUsername(userName: string) {
    this.userService.getUserByUsername(userName).subscribe((user: User) => {
      this.currUser = user;
    });
  }

  ngOnInit() {
    this.fetchUserByUsername('AngeloSilva');
    console.log(this.currUser);
    console.log('Intialized Home component');
  }
}

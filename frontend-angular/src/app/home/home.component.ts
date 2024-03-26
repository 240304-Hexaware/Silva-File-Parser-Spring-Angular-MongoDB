import { Component, Input } from '@angular/core';
import { UserService } from '../services/user.service';
import { User } from '../../types';
import { LoginComponent } from '../components/login/login.component';
import { FilesMainMenuComponent } from '../components/files-main-menu/files-main-menu.component';
import { ParsedFileViewComponent } from '../components/parsed-file-view/parsed-file-view.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    LoginComponent,
    FilesMainMenuComponent,
    ParsedFileViewComponent,
    CommonModule,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  constructor(private userService: UserService) {}
  isParsedFileVisible: boolean = false;

  //TODO: Delete when hardcoded user not needed
  hardcodedUser: User = {
    id: 0,
    username: 'InvalidUser',
    password: 'BadPassword',
    listOfSpecFileIds: [],
    listOfParsedFileIds: [],
  };

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
      //TODO: Delete when Hardcoded user not needed
      this.hardcodedUser = user;
    });
  }

  ngOnInit() {
    //TODO: Replace with generic user fetched from log in
    this.fetchUserByUsername('AngeloSilva');
    console.log(this.currUser);
  }

  setIsParsedFileVisible(isParsedFileVisible: boolean) {
    this.isParsedFileVisible = isParsedFileVisible;
  }
}

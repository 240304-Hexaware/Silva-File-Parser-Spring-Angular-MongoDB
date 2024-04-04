import { Component, Input } from '@angular/core';
import { UserService } from '../services/user.service';
import { EventFlatAndSpec, ParsedFile, User } from '../../types';
import { LoginComponent } from '../components/login/login.component';
import { FilesMainMenuComponent } from '../components/files-main-menu/files-main-menu.component';
import { ParsedFileViewComponent } from '../components/parsed-file-view/parsed-file-view.component';
import { CommonModule } from '@angular/common';
import { ParsedFileViewService } from '../components/services/parsed-file-view.service';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { error } from 'console';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    LoginComponent,
    FilesMainMenuComponent,
    ParsedFileViewComponent,
    CommonModule,
    ToastModule,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  providers: [MessageService],
})
export class HomeComponent {
  isParsedFileVisible: boolean = false;
  listOfParsedFiles: ParsedFile[] = [];

  constructor(
    private userService: UserService,
    private parsedFileViewService: ParsedFileViewService,
    private messageService: MessageService
  ) {}

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

  onParseFileChange(event: EventFlatAndSpec): void {
    this.parsedFileViewService.postParsedFile(event, this.currUser).subscribe({
      next: (data: ParsedFile[]) => {
        for (let ps of data) {
          this.listOfParsedFiles.push(ps);
        }
        this.messageService.add({
          severity: 'success',
          summary: 'Added ParsedFile',
          detail: `${event.file.name}`,
        });
      },
      error: (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Could not Add ParsedFile',
          detail: error.error,
        });
      },
    });
  }

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
    this.parsedFileViewService
      .fetchAllParsedFilesOfUser(this.currUser)
      .subscribe((data: ParsedFile[]) => {
        this.listOfParsedFiles = data;
      });
  }

  setIsParsedFileVisible(isParsedFileVisible: boolean) {
    this.isParsedFileVisible = isParsedFileVisible;
  }
}

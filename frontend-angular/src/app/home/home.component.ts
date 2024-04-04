import { Component, Input } from '@angular/core';
import { UserService } from '../services/user.service';
import { EventFlatAndSpec, ParsedFile, SpecFile, User } from '../../types';
import { LoginComponent } from '../components/login/login.component';
import { FilesMainMenuComponent } from '../components/files-main-menu/files-main-menu.component';
import { ParsedFileViewComponent } from '../components/parsed-file-view/parsed-file-view.component';
import { CommonModule } from '@angular/common';
import { ParsedFileViewService } from '../components/services/parsed-file-view.service';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { error } from 'console';
import { NewParsedFileViewComponent } from '../components/new-parsed-file-view/new-parsed-file-view.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    LoginComponent,
    FilesMainMenuComponent,
    ParsedFileViewComponent,
    NewParsedFileViewComponent,
    CommonModule,
    ToastModule,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  providers: [MessageService],
})
export class HomeComponent {
  isNewParsedFileVisible: boolean = false;
  isParsedFileVisible: boolean = false;
  listOfParsedFiles: ParsedFile[] = [];
  listOfNewParsedFiles: ParsedFile[] = [];
  specFileUsed: SpecFile | null = null;

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
        this.listOfNewParsedFiles = data;
        this.specFileUsed = event.specFile;

        this.messageService.add({
          severity: 'success',
          summary: 'Parsed FlatFile',
          detail: `${event.file.name}`,
        });
      },
      error: (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Could not parse Flat File',
          detail: error.error,
        });
      },
    });
    this.isNewParsedFileVisible = true;
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

  setIsParsedFileVisible(event: boolean) {
    this.isParsedFileVisible = event;
  }

  setIsNewParsedFileVisible(event: boolean) {
    this.isNewParsedFileVisible = event;
  }
}

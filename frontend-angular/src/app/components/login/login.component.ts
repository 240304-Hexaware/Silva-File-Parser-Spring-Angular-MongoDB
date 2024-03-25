import { Component, Input } from '@angular/core';
import { User } from '../../../types';
import { UserService } from '../../services/user.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  @Input() user: User = {
    username: '',
    password: '',
  };

  constructor(private userService: UserService) {}

  onLogin() {
    console.log('User clicked Log in button');
    console.log(this.user);
  }

  onRegister() {
    console.log('User clicked Register button');
  }
}

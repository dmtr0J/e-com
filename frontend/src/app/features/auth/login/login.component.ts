import {Component, inject, signal} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButton, MatIconButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {LoginForm} from './models/login-form.interface';
import {AuthService} from '../../../core/services/auth/auth.service';

@Component({
  selector: 'app-login',
  imports: [
    MatFormField,
    MatLabel,
    ReactiveFormsModule,
    MatIconButton,
    MatIcon,
    MatInputModule,
    MatButton,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  authService = inject(AuthService);
  loginForm: FormGroup<LoginForm>;

  constructor(private fb: FormBuilder) {
    this.loginForm = this.fb.nonNullable.group({
      email: ['', {
        validators: [Validators.required, Validators.email]
      }],
      password: ['', {
        validators: [Validators.required, Validators.minLength(6)]
      }],
    });
  }

  hide = signal(true);

  clickEvent(event: MouseEvent) {
    this.hide.set(!this.hide());
    event.stopPropagation();
  }

  onSubmit() {
    if (this.loginForm.valid) {
      this.authService.loginWithEmail(
        {
          email: this.loginForm.value.email!,
          password: this.loginForm.value.password!
        }
      ).subscribe(result => {
          console.log(result);
        }
      )
    }
  }

}

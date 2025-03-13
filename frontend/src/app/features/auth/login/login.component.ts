import {Component, signal, WritableSignal} from '@angular/core';
import {FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButton, MatIconButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {LoginForm} from './models/login-form.interface';
import {AuthService} from '../../../core/services/auth/auth.service';
import {Router} from '@angular/router';

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
  protected loginForm: FormGroup<LoginForm>;

  constructor(
    private authService: AuthService,
    private router: Router,
    private fb: NonNullableFormBuilder,
  ) {
    this.loginForm = this.fb.group({
      email: ['', {
        validators: [Validators.required, Validators.email]
      }],
      password: ['', {
        validators: [Validators.required, Validators.minLength(6)]
      }],
    });
  }

  isPasswordVisible: WritableSignal<boolean> = signal(false);

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.authService.loginWithEmail(this.loginForm.getRawValue())
        .subscribe(
          () => this.router.navigate([''])
        );
    }
  }

}

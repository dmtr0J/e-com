import {Component, inject, signal, WritableSignal} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {RegisterForm} from './models/register-form.interface';
import {MatError, MatFormField, MatLabel} from '@angular/material/form-field';
import {MatIcon} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatButton, MatIconButton} from '@angular/material/button';
import {AuthService} from '../../../core/services/auth/auth.service';
import {HttpClient} from '@angular/common/http';
import {validateUniqueEmail} from './validators/email-unique.validator';
import {JsonPipe} from '@angular/common';

@Component({
  selector: 'app-register',
  imports: [
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatIcon,
    MatInputModule,
    MatIconButton,
    MatButton,
    MatError,
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  protected registerForm: FormGroup<RegisterForm>;

  constructor(
    private authService: AuthService,
    private fb: FormBuilder
  ) {
    this.registerForm = this.fb.group<RegisterForm>({
      email: this.fb.nonNullable.control('', {
        validators: [Validators.required, Validators.email],
        asyncValidators: [validateUniqueEmail(inject(HttpClient))],
      }),
      password: this.fb.nonNullable.control('', {
        validators: [Validators.required, Validators.minLength(6)]
      }),
      phone: this.fb.control(null, {
        validators: Validators.pattern(/^[0-9]{10}$/)
      }),
      firstName: this.fb.control(null),
      middleName: this.fb.control(null),
      lastName: this.fb.control(null)
    });
  }

  isPasswordVisible: WritableSignal<boolean> = signal(false);

  onSubmit(): void {
    if (this.registerForm.valid) {
      this.authService.register(this.registerForm.getRawValue())
        .subscribe();
    }
  }

}

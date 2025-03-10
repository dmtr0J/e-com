import {Component, inject, signal} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {RegisterForm} from './models/register-form.interface';
import {MatError, MatFormField, MatLabel} from '@angular/material/form-field';
import {MatIcon} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatButton, MatIconButton} from '@angular/material/button';
import {AuthService} from '../../../core/services/auth/auth.service';
import {HttpClient} from '@angular/common/http';
import {JsonPipe} from '@angular/common';
import {validateUniqueEmail} from './validators/email-unique.validator';

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
    JsonPipe
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  authService = inject(AuthService);
  registerForm: FormGroup<RegisterForm>;

  constructor(private fb: FormBuilder) {
    this.registerForm = this.fb.nonNullable.group({
      email: ['', {
        validators: [Validators.required, Validators.email],
        asyncValidators: [validateUniqueEmail(inject(HttpClient))],
      }],
      password: ['', {
        validators: [Validators.required, Validators.minLength(6)]
      }],
      phone: ['', {
        validators: [Validators.pattern(/^[0-9]{10}$/)]
      }],
      firstName: [''],
      middleName: [''],
      lastName: ['']
    })
  }

  hide = signal(true);

  clickEvent(event: MouseEvent) {
    this.hide.set(!this.hide());
    event.stopPropagation();
  }

  onSubmit() {
    if (this.registerForm.valid) {
      this.authService.register(
        {
          email: this.registerForm.value.email!,
          password: this.registerForm.value.password!,
          phone: this.registerForm.value.phone!,
          firstName: this.registerForm.value.firstName!,
          middleName: this.registerForm.value.middleName!,
          lastName: this.registerForm.value.lastName!
        }
      ).subscribe(
        result => {
          console.log(result);
        }
      )
    }
  }

}

import {Component, signal} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {RegisterForm} from './models/register-form';
import {MatError, MatFormField, MatLabel} from '@angular/material/form-field';
import {MatIcon} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatButton, MatIconButton} from '@angular/material/button';

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
    MatError
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  registerForm: FormGroup<RegisterForm>;

  constructor(private fb: FormBuilder) {
    this.registerForm = this.fb.nonNullable.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      phone: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      firstName: [''],
      middleName: [''],
      lastName: ['']
    })
  }

  get emailControl() {
    return this.registerForm.get('email');
  }

  get passwordControl() {
    return this.registerForm.get('password');
  }

  get phoneControl() {
    return this.registerForm.get('phone');
  }

  get firstNameControl() {
    return this.registerForm.get('firstName');
  }

  get middleNameControl() {
    return this.registerForm.get('middleName');
  }

  get lastNameControl() {
    return this.registerForm.get('lastName');
  }

  hide = signal<boolean>(true);

  clickEvent(event: MouseEvent) {
    this.hide.set(!this.hide());
    event.stopPropagation();
  }

  onSubmit() {
    if (this.registerForm.valid) {
      console.log(this.registerForm.value)
    }
  }

}

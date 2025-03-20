import {Component, inject, OnInit} from '@angular/core';
import {ButtonComponent} from "../../../shared/components/button/button.component";
import {CheckboxComponent} from "../../../shared/components/checkbox/checkbox.component";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {InputComponent} from "../../../shared/components/input/input.component";
import {AuthService} from '../../../core/services/auth/auth.service';
import {RegisterForm} from './models/register-form.interface';
import {validateUniqueEmail} from '../../../core/validators/email-unique.validator';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-register-form',
  imports: [
    ButtonComponent,
    CheckboxComponent,
    FormsModule,
    InputComponent,
    ReactiveFormsModule
  ],
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.scss'
})
export class RegisterFormComponent implements OnInit {
  private authService: AuthService = inject(AuthService);
  private http: HttpClient = inject(HttpClient);
  private fb: FormBuilder = inject(FormBuilder);

  protected registerForm!: FormGroup<RegisterForm>;

  ngOnInit(): void {
    this.registerForm = this.fb.group<RegisterForm>({
      email: this.fb.nonNullable.control('', {
        validators: [Validators.required, Validators.email],
        asyncValidators: [validateUniqueEmail(this.http)],
      }),
      password: this.fb.nonNullable.control('', {
        validators: [Validators.required, Validators.minLength(6)]
      }),
      phone: this.fb.control(null, {
        validators: Validators.pattern(/^[0-9]{10}$/)
      }),
      firstName: this.fb.control(null),
      middleName: this.fb.control(null),
      lastName: this.fb.control(null),
      terms: this.fb.nonNullable.control(false, {
        validators: [Validators.required]
      }),
    });
  }

  markFormControlsAsTouched(): void {
    this.registerForm.markAllAsTouched();
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      // this.authService.register(this.registerForm.getRawValue())
      //   .subscribe();
    }
    console.log(this.registerForm.getRawValue());
  }

}

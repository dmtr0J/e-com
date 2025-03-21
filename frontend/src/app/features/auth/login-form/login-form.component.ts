import {Component, inject, OnInit} from '@angular/core';
import {ButtonComponent} from '../../../shared/components/button/button.component';
import {CheckboxComponent} from '../../../shared/components/checkbox/checkbox.component';
import {FormGroup, FormsModule, NonNullableFormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {InputComponent} from '../../../shared/components/input/input.component';
import {LoginForm} from './models/login-form.interface';
import {AuthService} from '../../../core/services/auth/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login-form',
  imports: [
    ButtonComponent,
    CheckboxComponent,
    FormsModule,
    InputComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.scss'
})
export class LoginFormComponent implements OnInit {
  private authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);
  private fb: NonNullableFormBuilder = inject(NonNullableFormBuilder);

  protected loginForm!: FormGroup<LoginForm>;

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', {
        validators: [Validators.required, Validators.email]
      }],
      password: ['', {
        validators: [Validators.required, Validators.minLength(6)]
      }],
      rememberMe: [false]
    });
  }

  markFormControlsAsTouched(): void {
    this.loginForm.markAllAsTouched();
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.authService.loginWithEmail(this.loginForm.getRawValue())
        .subscribe(
          // () => this.router.navigate([''])
        );
    }
  }



}

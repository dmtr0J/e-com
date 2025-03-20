import { Component } from '@angular/core';
import {LogoComponent} from "../../shared/components/logo/logo.component";
import {NgOptimizedImage} from "@angular/common";
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-auth',
  imports: [
    LogoComponent,
    NgOptimizedImage,
    RouterOutlet
  ],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.scss'
})
export class AuthComponent {

}

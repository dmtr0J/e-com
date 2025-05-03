import {Component, inject} from '@angular/core';
import {AuthService} from '../../core/services/auth/auth.service';
import {map} from 'rxjs';
import {HeaderComponent} from '../../shared/layouts/header/header.component';

@Component({
  selector: 'app-home',
  imports: [
    HeaderComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
}

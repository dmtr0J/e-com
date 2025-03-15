import {Component, inject} from '@angular/core';
import {AuthService} from '../../core/services/auth/auth.service';
import {map} from 'rxjs';
import {HeaderComponent} from '../../shared/layouts/header/header.component';
import {FooterComponent} from '../../shared/layouts/footer/footer.component';

@Component({
  selector: 'app-home',
  imports: [
    HeaderComponent,
    FooterComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
}

import { Component } from '@angular/core';
import {AdminSidebarComponent} from './components/admin-sidebar/admin-sidebar.component';
import {AdminHeaderComponent} from './components/admin-header/admin-header.component';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-admin-panel',
  imports: [
    AdminSidebarComponent,
    AdminHeaderComponent,
    RouterOutlet
  ],
  templateUrl: './admin-panel.component.html',
  styleUrl: './admin-panel.component.scss'
})
export class AdminPanelComponent {

}

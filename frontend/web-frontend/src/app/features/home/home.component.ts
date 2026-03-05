import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/auth/auth.service';

// Importaciones de PrimeNG
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, CardModule, ButtonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss' // O .css dependiendo de cómo lo generaste
})
export class HomeComponent {
  // Inyectamos nuestro servicio de autenticación
  authService = inject(AuthService);

  // Obtenemos los datos del usuario logueado
  claims = this.authService.identityClaims;

  logout() {
    this.authService.logout();
  }
}

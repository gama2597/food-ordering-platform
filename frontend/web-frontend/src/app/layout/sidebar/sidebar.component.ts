import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { MenuModule } from 'primeng/menu';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule, MenuModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {
  // Aquí definimos las opciones del menú
  items: MenuItem[] = [
    { label: 'Inicio', icon: 'pi pi-home', routerLink: '/home' },
    { label: 'Dashboard', icon: 'pi pi-chart-bar', routerLink: '/dashboard' },
    { label: 'Productos', icon: 'pi pi-box', routerLink: '/productos' },
    { label: 'Pedidos', icon: 'pi pi-shopping-cart', routerLink: '/pedidos' },
    { label: 'Usuarios', icon: 'pi pi-users', routerLink: '/usuarios' }
  ];
}

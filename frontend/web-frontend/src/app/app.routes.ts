import { Routes } from '@angular/router';
import { AppLayoutComponent } from './layout/app-layout/app-layout.component';
import { HomeComponent } from './features/home/home.component'; // O la feature que usemos de inicio

export const routes: Routes = [
  {
    path: '',
    component: AppLayoutComponent, // El cascarón principal
    children: [
      { path: 'home', component: HomeComponent },
      { path: 'usuarios', loadComponent: () => import('./features/usuarios/pages/usuarios/usuarios.component').then(m => m.UsuariosComponent) },
      { path: '', redirectTo: 'home', pathMatch: 'full' }
    ]
  },
  { path: '**', redirectTo: '' }
];

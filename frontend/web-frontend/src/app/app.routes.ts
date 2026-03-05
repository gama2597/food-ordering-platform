import { Routes } from '@angular/router';
import { HomeComponent } from 'src/app/features/home/home.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: '**', redirectTo: '' } // Cualquier ruta no encontrada te manda al inicio
];

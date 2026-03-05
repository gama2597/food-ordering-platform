import {
  ApplicationConfig,
  APP_INITIALIZER,
  importProvidersFrom,
} from '@angular/core';
import { provideRouter } from '@angular/router';
// Importamos withInterceptors
import {
  provideHttpClient,
  withFetch,
  withInterceptors,
} from '@angular/common/http';
import { OAuthModule } from 'angular-oauth2-oidc';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeng/themes/aura';
import { MessageService } from 'primeng/api';

import { routes } from './app.routes';
import { AuthService } from './core/auth/auth.service';
// Importamos nuestro nuevo interceptor
import { authInterceptor } from './core/interceptors/auth.interceptor';
import { errorInterceptor } from './core/interceptors/error.interceptor';

export function initializeAuth(authService: AuthService) {
  return (): Promise<boolean> => authService.initAuth();
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    // 🚀 Registramos el interceptor aquí:
    provideHttpClient(withFetch(), withInterceptors([authInterceptor, errorInterceptor])),
    importProvidersFrom(OAuthModule.forRoot()),

    {
      provide: APP_INITIALIZER,
      useFactory: initializeAuth,
      deps: [AuthService],
      multi: true,
    },

    provideAnimationsAsync(),
    providePrimeNG({
      theme: {
        preset: Aura,
        options: {
          darkModeSelector: 'none',
        },
      },
    }),
    MessageService,
  ],
};

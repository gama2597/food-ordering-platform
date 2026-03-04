import { Injectable, signal, computed } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { authConfig } from 'src/app/core/auth/auth.config';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // Estado reactivo con Signals
  private authState = signal(false);
  public isLoggedIn = computed(() => this.authState());

  constructor(private oauthService: OAuthService) {}

  // Se ejecuta antes de que la app se dibuje
  public initAuth(): Promise<boolean> {
    this.oauthService.configure(authConfig);
    this.oauthService.setupAutomaticSilentRefresh();

    return this.oauthService.loadDiscoveryDocumentAndLogin().then(loggedIn => {
      this.authState.set(loggedIn);

      if (loggedIn) {
        console.log('✅ Autenticado en Angular 18. Token:', this.token);
      } else {
        console.log('⏳ Redirigiendo a Keycloak...');
        this.oauthService.initCodeFlow();
      }
      return loggedIn;
    });
  }

  get token() { return this.oauthService.getAccessToken(); }
  get identityClaims() { return this.oauthService.getIdentityClaims(); }
  logout() { this.oauthService.logOut(); }
}

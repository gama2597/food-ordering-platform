import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from 'src/app/core/auth/auth.service';
import { environment } from 'src/environments/environment';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // Inyectamos nuestro servicio de autenticación
  const authService = inject(AuthService);
  const token = authService.token;

  // Verificamos si la petición va dirigida a nuestro backend de Spring Boot
  const isApiUrl = req.url.startsWith(environment.apiUrl);

  // Si tenemos token y la URL es de nuestra API, clonamos la petición y le inyectamos el token
  if (token && isApiUrl) {
    const clonedRequest = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });

    // Dejamos pasar la petición modificada
    return next(clonedRequest);
  }

  // Si no es para nuestra API, la dejamos pasar tal cual sin modificarla
  return next(req);
};

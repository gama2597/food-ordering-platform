import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { ToastService } from '../services/toast.service';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(AuthService);
  const toast = inject(ToastService);

  return next(req).pipe(
    catchError((err: unknown) => {
      if (err instanceof HttpErrorResponse) {
        const status = err.status;

        if (status === 0) {
          toast.error('Sin conexión', 'No se pudo conectar al servidor.');
        } else if (status === 401) {
          toast.warn('Sesión expirada', 'Vuelve a iniciar sesión.');
          auth.login(); // inicia flow OIDC
        } else if (status === 403) {
          toast.error('No autorizado', 'No tienes permisos para esta acción.');
        } else if (status >= 500) {
          toast.error('Error del servidor', 'Intenta nuevamente en unos segundos.');
        } else if (status === 400) {
          // si backend manda estructura de error, intenta mostrar un detalle útil
          const msg = (err.error?.message || err.message || 'Solicitud inválida');
          toast.warn('Validación', msg);
        } else {
          toast.error('Error', err.message);
        }
      } else {
        toast.error('Error', 'Ocurrió un error inesperado.');
      }

      return throwError(() => err);
    })
  );
};

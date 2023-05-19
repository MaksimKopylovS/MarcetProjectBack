import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AuthService} from '../services/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const user = this.authService.userValue
    const isLoggedIn = user && user.jwtResponse?.token;

    if (isLoggedIn) {
      let line = `Bearer ${user.jwtResponse?.token}`
      request = request.clone({
        setHeaders: {
          Authorization: line
        }
      });
    }
    return next.handle(request);
  }
}

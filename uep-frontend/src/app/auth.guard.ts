import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from './user-service';
import { map, catchError } from 'rxjs/operators';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private userService: UserService, private router: Router) {}

  canActivate(): Observable<boolean> {
    return this.userService.getProfile().pipe(
      map(user => {
        if (user) {
          return true; // Allow access if user is logged in
        }
        this.router.navigate(['/landing-page']); // Redirect if not authenticated
        return false;
      }),
      catchError(() => {
        this.router.navigate(['/landing-page']); // Redirect if API fails
        return of(false);
      })
    );
  }
}

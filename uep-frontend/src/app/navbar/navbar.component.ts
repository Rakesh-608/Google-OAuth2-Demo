import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { User } from '../user';
import { UserService } from '../user-service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  user: User | null = null; // Store user data

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.userService.getProfile().subscribe(
      (user) => {
        this.user = user;
      },
      () => {
        this.user = null; // If request fails, assume not logged in
      }
    );
  }

  loginWithGoogle() {
    window.location.href = 'http://localhost:8080/oauth2/authorization/google';
  }

  logout(): void {
    this.userService.logout();
    this.user = null;
    this.router.navigate(['/landing-page']);
  }
}

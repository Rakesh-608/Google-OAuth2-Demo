import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../user';
import { UserService } from '../user-service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  // user: User | null = null;
  // user!: User;

  // constructor(private userService: UserService, private router: Router) {}

  // ngOnInit() {
  //   this.userService.getProfile().subscribe(
  //     (profile) => {
  //       this.user = profile;
  //     },
  //     (error) => {
  //       console.error('Error fetching user profile:', error);
  //     }
  //   );
  // }

  user: User = {
    id: 0,
    email: '',
    name: '',
    picture: '',
    collegeName: '',
    experience: ''
  }; 
  
  // user!: User;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {
    // this.loadProfile();
    this.userService.getProfile().subscribe(
      user => {
        console.log('Fetched User:', user);
        this.user = user;
      },
      error => console.error('Error fetching profile:', error)
    );
  }

  loadProfile() {
    this.userService.getProfile().subscribe(
          user => {
            console.log('Fetched User:', user);
            this.user = user;
          },
          error => console.error('Error fetching profile:', error)
        );
  }

  continueRegistration() {
    this.router.navigate(['/register']); // Navigate to registration page
  }
}

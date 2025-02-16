import { CommonModule, NgIf } from '@angular/common';
import { Component, NgModule } from '@angular/core';
import { FormsModule, NgForm, NgModel, ReactiveFormsModule } from '@angular/forms';
import { User } from '../user';
import { UserService } from '../user-service';

@Component({
  selector: 'app-profile-component',
  standalone: true,
  imports: [ReactiveFormsModule, FormsModule, CommonModule],
  templateUrl: './profile-component.component.html',
  styleUrl: './profile-component.component.css'
})
export class ProfileComponentComponent {

  user: User = {
    id: 0,
    email: '',
    name: '',
    picture: '',
    collegeName: '',
    experience: ''
  }; 
  
  // user!: User;

  constructor(private userService: UserService) { }

  gOnInit() {
    this.loadProfile();
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

  updateProfile() {
    this.userService.updateProfile(this.user).subscribe(
      updatedProfile => this.user = updatedProfile
    );
  }
  

  onSubmit() {
    if (this.user) {
      this.userService.updateProfile(this.user).subscribe(
        updatedUser => {
          this.user = updatedUser;
          alert('Profile updated successfully!');
        }
      );
    }
  }
}

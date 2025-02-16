import { CommonModule } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbAlert } from '@ng-bootstrap/ng-bootstrap';
import { Subject } from 'rxjs';
import { User } from '../user';
import { UserService } from '../user-service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, FormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  user: User = {
    id: 0,
    email: '',
    name: '',
    picture: '',
    collegeName: '',
    experience: ''
  };
  

  step: number = 1;

  constructor(private userService: UserService) {}

  ngOnInit() {
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

  nextStep() {
    if (this.step === 1 && this.user.collegeName) {
      console.log(this.user.experience, this.user.collegeName);
      this.step = 2;
    }
  }

  submitForm() {
    this.userService.updateProfile(this.user).subscribe(
      updatedUser => {
        this.user = updatedUser;
        alert('Profile updated successfully!');
      }
    );
  }
}

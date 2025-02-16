import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  getProfile(): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/user`, {
      withCredentials: true // Ensures cookies/session authentication
    });
  }

  updateProfile(user: User): Observable<User> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.put<User>(`${this.apiUrl}/user`, user, {
      headers: headers,
      withCredentials: true // Required for session-based authentication
    });
  }


  logout(): void {
    this.http.post(`${this.apiUrl}/logout`, {}, { withCredentials: true }).subscribe(() => {
      window.location.href = '/landing-page'; // Redirect to landing page after logout
    });
  }
  
}

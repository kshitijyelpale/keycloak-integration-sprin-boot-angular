import {Component, OnInit} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { OAuthService } from 'angular-oauth2-oidc';
import {HttpClient, HttpErrorResponse, HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  title = 'frontend';
  permission: Set<string> = new Set();
  api = 'http://localhost:8083'
  message1 = ''
  message2 = ''

  constructor(
    private readonly oAuthService: OAuthService,
    private readonly http: HttpClient
  ) {
  }

  ngOnInit() {
    this.http.get<{ message: string }>(`${this.api}/home`, {
      headers: { 'Authorization': this.oAuthService.authorizationHeader() }
    }).subscribe((response) => {
      this.title = response.message;
    });

    this.http.get<Set<string>>(`${this.api}/permissions`, {
      headers: { 'Authorization': this.oAuthService.authorizationHeader() }
    }).subscribe((response: Set<string>) => {
      this.permission = response
    });
  }

  getPage1() {
    this.http.get<string>(`${this.api}/page1`, {
      headers: { 'Authorization': this.oAuthService.authorizationHeader() },
      responseType: "text" as "json"
    }).subscribe((response: string) => {
      console.log(response);
      this.message1 = response;
    }, (err: HttpErrorResponse) => {
      if (err.status === 403) {
        this.message1 = `No access: ${err.message}`;
      }
    });
  }

  getPage2() {
    this.http.get<string>(`${this.api}/page2`, {
      headers: { 'Authorization': this.oAuthService.authorizationHeader() },
      responseType: "text" as "json"
    }).subscribe((response: string) => {
      console.log(response);
      this.message2 = response;
    },(err: HttpErrorResponse) => {
      if (err.status === 403) {
        this.message2 = `No access: ${err.message}`;
      }
    });
  }

  logout() {
    this.oAuthService.logOut();
  }
}

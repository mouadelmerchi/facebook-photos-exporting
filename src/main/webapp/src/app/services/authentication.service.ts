import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

@Injectable()
export class AuthenticationService {
    private authUrl: string = 'auth';
    private headers = new Headers({ 'Content-Type': 'application/json' });

    constructor(private http: Http) { }

    login(email: string, password: string): Observable<boolean> {
        return this.http.post(this.authUrl, JSON.stringify({ email: email, password: password }), new RequestOptions({ headers: this.headers }))
            .map((response: Response) => {
                // login successful if there's a user in the response
                let token = response.json() && response.json().token;
                if (token) {
                    // store user details in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify({ email: email, token: token }));

                    // return true to indicate successful login
                    return true;
                } else {
                    // return false to indicate failed login
                    return false;
                }
            }).catch((error: any) => Observable.throw(error.json().error || 'Server error'));
    }

    logout(): void {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
    }
}
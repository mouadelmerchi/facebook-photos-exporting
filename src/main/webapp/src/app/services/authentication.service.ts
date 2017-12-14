import { Injectable }                 from '@angular/core';
import {
    HttpClient,
    HttpHeaders,
    HttpErrorResponse
}                                      from '@angular/common/http';

import { Observable }                  from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

import { StorageService } from './storage.service';

import { AuthenticationToken } from '../models/index';

@Injectable()
export class AuthenticationService {
    private authUrl: string = 'auth';
    private refreshUrl: string = 'refresh';
    private headers = new HttpHeaders().set('Content-Type', 'application/json');

    constructor(private storageService: StorageService, private http: HttpClient) { }

    login(email: string, password: string): Observable<boolean | {}> {
        return this.http.post<AuthenticationToken>(this.authUrl, JSON.stringify({ email: email, password: password }), { headers: this.headers })
            .map(data => {
                // login successful if there's a user in the response
                let token = data.token;
                if (token) {
                    // store user details in local storage to keep user logged in between page refreshes
                    this.storageService.storeCurrentUser(email, token);

                    // return true to indicate successful login
                    return true;
                } else {
                    // return false to indicate failed login
                    return false;
                }
            })
            .catch(err => {
                let errObj: any;
                if (err.error instanceof Error) {
                    // A client-side or network error occurred. Handle it accordingly.
                    errObj = { status: 200, message: { body: 'Client side error occurred' }, error: { reason: 'Client Error', body: err.error.message } };
                } else if (err instanceof HttpErrorResponse) {
                    // The backend returned an unsuccessful response code.
                    // The response body may contain clues as to what went wrong,
                    errObj = err.error || { status: 500, message: { body: 'System unavailable' }, error: { reason: 'Error', body: 'Server error' } };
                }
                throw (errObj);
            });
    }

    refreshToken(): Observable<boolean | {}> {
        return this.http.get<AuthenticationToken>(this.refreshUrl)
            .map(data => {
                // login successful if there's a user in the response
                let refreshedToken = data.token;
                if (refreshedToken) {
                    // get update current user's token
                    this.storageService.updateToken(refreshedToken);

                    // return true to indicate successful login
                    return true;
                } else {
                    // return false to indicate failed login
                    return false;
                }
            })
            .catch(err => {
                let errObj: any;
                if (err.error instanceof Error) {
                    // A client-side or network error occurred. Handle it accordingly.
                    errObj = { status: 200, message: { body: 'Client side error occurred' }, error: { reason: 'Client Error', body: err.error.message } };
                } else if (err instanceof HttpErrorResponse) {
                    // The backend returned an unsuccessful response code.
                    // The response body may contain clues as to what went wrong,
                    errObj = err.error || { status: 500, message: { body: 'System unavailable' }, error: { reason: 'Error', body: 'Server error' } };
                }
                throw (errObj);
            });
    }

    logout(): void {
        // remove user from local storage to log user out
        this.storageService.removeCurrentUser();
    }
}
import { Injectable }            from '@angular/core';
import {
    HttpClient,
    HttpHeaders,
    HttpErrorResponse
}                                from '@angular/common/http';

import { Observable }            from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

import {
    User,
    AuthenticationTokenResponse
}                                from '../models/index';

import { StorageService } from './storage.service';

@Injectable()
export class UserService {

    constructor(private storage: StorageService, private http: HttpClient) { }

    create(user: User): Observable<boolean | {}> {
        return this.http.post<AuthenticationTokenResponse>('/auth/user', user) // this.getAuthorizationHeaders()
            .map(data => {
                // user created and authenticated automatically
                let token = data.token;
                if (token) {
                     this.storage.storeCurrentUser(JSON.stringify({ email: user.email, token: token }));

                    // return true to indicate successful login
                    return true;
                } else {
                    // return false to indicate failed login
                    return false;
                }
            })
            .catch((err: any) => {
                let errObj: any;
                if (err.error instanceof Error) {
                    // A client-side or network error occurred. Handle it accordingly.
                    errObj = { status: 200, message: { body: 'Client side error occurred' }, error: { reason: 'Client Error', body: err.error.message } };
                } else {
                    // The backend returned an unsuccessful response code.
                    // The response body may contain clues as to what went wrong,
                    errObj = err.error || { status: 500, message: { body: 'System unavailable' }, error: { reason: 'Error', body: 'Server error' } };
                }
                throw(errObj);
            });
    }

    private getAuthorizationHeaders() {
        return {
            headers: new HttpHeaders().set('Authorization', `Bearer ${this.storage.getToken()}`)
        };
    }
}
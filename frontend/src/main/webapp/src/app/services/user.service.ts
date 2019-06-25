import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { User, AuthenticationToken } from '../models/index';

import { StorageService } from './storage.service';

@Injectable()
export class UserService {

    constructor(private storageService: StorageService, private http: HttpClient) { }

    create(user: User): Observable<boolean | {}> {
        return this.http.post<AuthenticationToken>('/auth/user', user) // this.getAuthorizationHeaders()
            .pipe(
                map(data => {
                    // user created and authenticated automatically
                    const token = data.token;
                    if (token) {
                        this.storageService.storeCurrentUser(user.email, token);

                        // return true to indicate successful login
                        return true;
                    } else {
                        // return false to indicate failed login
                        return false;
                    }
                }),
                catchError((err: any) => {
                    let errObj: any;
                    if (err.error instanceof Error) {
                        // A client-side or network error occurred. Handle it accordingly.
                        errObj = { status: 200, message: { body: 'Client side error occurred' },
                            error: { reason: 'Client Error', body: err.error.message } };
                    } else {
                        // The backend returned an unsuccessful response code.
                        // The response body may contain clues as to what went wrong,
                        errObj = err.error || { status: 500, message: { body: 'System unavailable' },
                            error: { reason: 'Error', body: 'Server error' } };
                    }
                    return throwError(errObj);
                })
            );
    }

    private getAuthorizationHeaders() {
        return {
            headers: new HttpHeaders().set('Authorization', `Bearer ${this.storageService.getField('token')}`)
        };
    }
}

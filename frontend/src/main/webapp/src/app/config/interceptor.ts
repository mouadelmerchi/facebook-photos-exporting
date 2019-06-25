import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpEvent, HttpHandler, HttpRequest, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { StorageService } from '../services/index';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private storageService: StorageService, private router: Router) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        request = request.clone({
            setHeaders: {
                Authorization: `Bearer ${this.storageService.getField('token')}`
            }
        });

        // install an error handler
        return next.handle(request)
            .pipe(
                tap((event: HttpEvent<any>) => {
                    if (event instanceof HttpResponse) {
                        console.log('Response intercepted');
                    }
                }),
                catchError((err: any) => {
                    if (err instanceof HttpErrorResponse) {
                        // The backend returned an unsuccessful response code.
                        // The response body may contain clues as to what went wrong,
                        console.log(`Backend returned code ${err.status}, body was: `);
                        console.log(err.error);

                        if (err.status === 401) {
                            console.log(`Status (${err.status}) => ${err.error.reason}: ${err.error.body}`);
                            this.router.navigate(['/login']);
                            //                        authService.refreshToken()
                            //                            .subscribe(result => {
                            //                                if (result === true) {
                            //                                    // refresh token successful
                            //                                } else {
                            //                                    // refresh token failed
                            //                                    // TODO
                            //                                }
                            //                            }, err => {
                            //
                            //                            });
                        }
                    }
                    return throwError(err);
                })
            );
    }
}

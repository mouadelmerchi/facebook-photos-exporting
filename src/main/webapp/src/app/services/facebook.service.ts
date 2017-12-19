import { Injectable }                 from '@angular/core';
import {
    HttpClient,
    HttpResponse,
    HttpErrorResponse
}                                      from '@angular/common/http';

import { Observable }                  from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

import { AlbumsListResponse, 
         PhotosListResponse 
}                                      from '../models/index';

@Injectable()
export class FacebookService {
    private fbConnectionUrl = "/api/connect/facebook";
    private fbAlbumsUrl: string = '/api/facebook/albums';

    constructor(private http: HttpClient) { }
    
    connect(): Observable<HttpResponse<string>> {
        return this.http.post<string>(this.fbConnectionUrl, null, {observe: 'response'});
    }
    
    getAlbums(): Observable<any> {
        return this.http.get<AlbumsListResponse>(this.fbAlbumsUrl)
            .map(data => {
                let tuple: any = {
                    connected: false,
                    title: 'Connect to Facebook',
                    albums: [],
                    currentPage: 0,
                    pageSize: 0
                };
                if (data) {
                    tuple.connected = true;
                    tuple.title = 'Facebook Albums';
                    tuple.albums = data.albums || [];
                    tuple.currentPage = data.currentPage || 0;
                    tuple.pageSize = data.pageSize || 0;
                }
                return tuple;
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
    
     getAlbumPhotos(albumId: string): Observable<any> {
        return this.http.get<PhotosListResponse>(this.fbAlbumsUrl + '/' + albumId)
            .map(data => {
                let tuple: any = {
                    photos: [],
                    albumName: '',
                    currentPage: 0,
                    pageSize: 0
                };
                if (data) {
                    tuple.photos = data.photos || [];
                    tuple.albumName = data.albumName || '';
                    tuple.currentPage = data.currentPage || 0;
                    tuple.pageSize = data.pageSize || 0;
                }
                return tuple;
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
}
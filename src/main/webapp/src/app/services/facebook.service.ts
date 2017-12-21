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
    private fbAlbumsUrl: string = '/api/facebook/albums';

    constructor(private http: HttpClient) { }

    getAlbums(page: number): Observable<any> {
        return this.http.get<AlbumsListResponse>(this.fbAlbumsUrl + "/page/" + page)
            .map(data => {
                let tuple: any = {
                    connected: false,
                    title: 'Connect to Facebook',
                    albums: [],
                    totalCount: 0,
                    pageSize: 0,
                    pagesToShow: 0
                };
                if (data) {
                    tuple.connected = true;
                    tuple.title = 'Facebook Albums';
                    tuple.albums = data.albums || [];
                    tuple.totalCount = data.totalCount || 0;
                    tuple.pageSize = data.pageSize || 0;
                    tuple.pagesToShow = data.pagesToShow || 0;
                }
                return tuple;
            })
            .catch(err => {
                let errObj: any = err || { status: 500, error: 'System error. Please try again later' };
                throw (errObj);
            });
    }

    getAlbumPhotos(albumId: string, page: number): Observable<any> {
        return this.http.get<PhotosListResponse>(this.fbAlbumsUrl + '/' + albumId + "/page/" + page)
            .map(data => {
                let tuple: any = {
                    photos: [],
                    albumName: '',
                    totalCount: 0,
                    pageSize: 0,
                    pagesToShow: 0
                };
                if (data) {
                    tuple.photos = data.photos || [];
                    tuple.albumName = data.albumName || '';
                    tuple.totalCount = data.totalCount || 0;
                    tuple.pageSize = data.pageSize || 0;
                    tuple.pagesToShow = data.pagesToShow || 0;
                }
                return tuple;
            })
            .catch(err => {
                let errObj: any = err || { status: 500, error: 'System error. Please try again later' };
                throw (errObj);
            });
    }
}
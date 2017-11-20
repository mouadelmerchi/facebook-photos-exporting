import { Component, OnInit } from '@angular/core';
import { Router }            from '@angular/router';

import { FacebookService } from '../services/index';

@Component({
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
    tuple: any;

    constructor(
        private facebookService: FacebookService
    ) { }

    ngOnInit(): void {
        this.facebookService.getAlbums()
            .subscribe(result => {
                this.tuple = result;
                console.log(this.tuple);
                for (let album in this.tuple.albums) {
                    if (!album.coverPhoto) {
                        album.coverPhotoUrl = "/src/assets/img/default-cover.png";   
                    } else {
                        // TODO: import cover photo from server
                    }
                }
            }, err => {
                console.log("Status (" + err.status + ") => " + err.error.reason + ": " + err.error.body);
            });
    }
    
    onGetAlbumPhotos(albumId: number): void {
    
    }
}
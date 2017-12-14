import { Component, OnInit } from '@angular/core';
import { Router }            from '@angular/router';

import { StorageService, 
         FacebookService 
}                            from '../services/index';

@Component({
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
    tuple: any;
    userEmail: string;
    
    constructor(private storageService: StorageService, 
                private facebookService: FacebookService, 
                private router: Router) { }

    ngOnInit(): void {
        this.userEmail = this.storageService.getField("email");
        this.facebookService.getAlbums()
            .subscribe(result => {
                this.tuple = result;
                console.log(this.tuple);
                for (let i in this.tuple.albums) {
                    if (!this.tuple.albums[i].coverPhotoUri) {
                        this.tuple.albums[i].coverPhotoUri = "/src/assets/img/default-cover.png";
                    }
                }
            }, err => {
                console.log("Status (" + err.status + ") => " + err.error.reason + ": " + err.error.body);
            });
    }

    connectToFacebook(): void {
        this.facebookService.connect()
            .subscribe(response => {
                let location = response.headers.get('Location');
                console.log("New location: " + location);
                this.redirect(location);
            });
    }

    onGetAlbumPhotos(albumId: number): void {

    }

    private redirect(location: string): void {
        if (location) {
            window.location.href = location;
        }
    }
}
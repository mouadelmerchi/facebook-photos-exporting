import { Component, OnInit } from '@angular/core';

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
        private facebookService: FacebookService) { }

    ngOnInit(): void {
        this.userEmail = this.storageService.getField("email");
        this.facebookService.getAlbums()
            .subscribe(result => {
                this.tuple = result;
                for (let i in this.tuple.albums) {
                    if (!this.tuple.albums[i].coverPhotoUri) {
                        this.tuple.albums[i].coverPhotoUri = "/src/assets/img/default-cover.png";
                    }
                }
            }, err => {
                console.log("Status (" + err.status + ") => " + err.error.reason + ": " + err.error.body);
            });
    }
}
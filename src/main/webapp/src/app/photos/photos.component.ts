import { Component, OnInit } from '@angular/core';
import { ActivatedRoute }    from '@angular/router';

import { StorageService,
         FacebookService
}                            from '../services/index';

@Component({
    selector: 'photos',
    templateUrl: './photos.component.html',
    styleUrls: ['./photos.component.css']
})
export class PhotosComponent implements OnInit {
    private subscription: any;
    tuple: any;

    constructor(private storageService: StorageService,
        private facebookService: FacebookService,
        private route: ActivatedRoute) { }

    ngOnInit(): void {
        this.subscription = this.route.params.subscribe(params => {
            this.facebookService.getAlbumPhotos(params['id'])
                .subscribe(result => {
                    this.tuple = result;
                    console.log(this.tuple);
                    for (let i in this.tuple.photos) {
                        if (!this.tuple.photos[i].photoUri) {
                            this.tuple.photos[i].photoUri = "/src/assets/img/default-cover.png";
                        }
                    }
                }, err => {
                    console.log("Status (" + err.status + ") => " + err.error.reason + ": " + err.error.body);
                });
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
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
    albumId: string;
    currentPage: number = 1;
    loading: boolean = false;

    constructor(private storageService: StorageService,
        private facebookService: FacebookService,
        private route: ActivatedRoute) { }

    ngOnInit(): void {
        this.subscription = this.route.params.subscribe(params => {
            this.albumId = params['id'];
            this.getPhotos();
        });
    }

    getPhotos(): void {
        this.loading = false;
        this.facebookService.getAlbumPhotos(this.albumId, this.currentPage)
            .subscribe(result => {
                this.tuple = result;
                console.log(this.tuple);
                for (let i in this.tuple.photos) {
                    if (!this.tuple.photos[i].photoUri) {
                        this.tuple.photos[i].photoUri = "/src/assets/img/default-cover.png";
                    }
                }
                this.loading = false;
            }, err => {
                console.log("Status (" + err.status + ") => " + err.error);
            });
    }

    prevPage(): void {
        this.currentPage--;
        this.getPhotos();
    }

    goToPage(n: number): void {
        this.currentPage = n;
        this.getPhotos();
    }

    nextPage(): void {
        this.currentPage++;
        this.getPhotos();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
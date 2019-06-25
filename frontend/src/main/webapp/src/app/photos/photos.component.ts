import { Component, AfterViewInit, ViewChild, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { FacebookService } from '../services/index';

@Component({
    selector: 'app-photos',
    templateUrl: './photos.component.html',
    styleUrls: ['./photos.component.scss']
})
export class PhotosComponent implements AfterViewInit, OnDestroy {

    @ViewChild('photoModal', {static: false}) public photoModal: { show: () => void; };

    private subscription: any;
    tuple: any;
    albumId: string;
    currentPage = 1;
    loading = false;
    photoUri: string;

    constructor(private facebookService: FacebookService,
        private route: ActivatedRoute) { }

    ngAfterViewInit(): void {
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
                console.log(this.tuple.photos[0]);
                this.loading = false;
            }, err => {
                console.log(`Status (${err.status}) => ${err.error}`);
            });
    }

    showPhoto(uri: string): void {
        this.photoUri = uri;
        this.photoModal.show();
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

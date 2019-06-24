import { Component, AfterViewInit, ViewChild, ViewContainerRef } from '@angular/core';
import { ActivatedRoute }    from '@angular/router';

import { StorageService,
         FacebookService
}                            from '../services/index';

import { Photo }             from '../models/index';

@Component({
    selector: 'photos',
    templateUrl: './photos.component.html',
    styleUrls: ['./photos.component.scss']
})
export class PhotosComponent implements AfterViewInit {
    
    @ViewChild('photoModal', {static: false}) public photoModal;
    
    private subscription: any;
    tuple: any;
    albumId: string;
    currentPage: number = 1;
    loading: boolean = false;
    photoUri: string;

    constructor(private storageService: StorageService,
        private facebookService: FacebookService,
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
                console.log("Status (" + err.status + ") => " + err.error);
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
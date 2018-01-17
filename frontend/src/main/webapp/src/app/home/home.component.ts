import { Component, OnInit } from '@angular/core';

import { StorageService,
    FacebookService
}                            from '../services/index';

@Component({
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
    tuple: any;
    userEmail: string;
    currentPage: number = 1;
    loading: boolean = false;

    constructor(private storageService: StorageService,
        private facebookService: FacebookService) { }

    ngOnInit(): void {
        this.userEmail = this.storageService.getField("email");
        this.getAlbums();
    }

    getAlbums(): void {
        this.loading = false;
        this.facebookService.getAlbums(this.currentPage)
            .subscribe(result => {
                this.tuple = result;
                this.loading = false;
            }, err => {
                console.log("Status (" + err.status + ") => " + err.error);
            });
    }

    prevPage(): void {
        this.currentPage--;
        this.getAlbums();
    }

    goToPage(n: number): void {
        this.currentPage = n;
        this.getAlbums();
    }

    nextPage(): void {
        this.currentPage++;
        this.getAlbums();
    }
}
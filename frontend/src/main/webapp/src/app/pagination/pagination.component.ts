import { Component, OnInit, EventEmitter, Input, Output } from '@angular/core';

@Component({
    selector: 'app-pagination',
    templateUrl: './pagination.component.html',
    styleUrls: ['./pagination.component.scss']
})
export class PaginationComponent implements OnInit {

    @Input() currentPage: number; // the current page
    @Input() totalCount: number; // how many total items there are in all pages
    @Input() pageSize: number; // how many items we want to show per page
    @Input() pagesToShow: number; // how many pages between next/prev
    @Input() loading: boolean; // check if content is being loaded

    @Output() goPrev = new EventEmitter<boolean>();
    @Output() goPage = new EventEmitter<number>();
    @Output() goNext = new EventEmitter<boolean>();

    constructor() { }

    ngOnInit(): void { }

    onPrev(): void {
        this.goPrev.emit(true);
    }

    onPage(n: number): void {
        if (n !== this.currentPage) {
            this.goPage.emit(n);
        }
    }

    onNext(next: boolean): void {
        this.goNext.emit(next);
    }

    lastPage(): boolean {
        return this.pageSize * this.currentPage > this.totalCount;
    }

    getPages(): number[] {
        const c = Math.ceil(this.totalCount / this.pageSize);
        const p = this.currentPage || 1;
        const pagesToShow = this.pagesToShow || 10;
        const pages: number[] = [];
        pages.push(p);
        const times = pagesToShow - 1;
        let min: number;
        let max: number;
        for (let i = 0; i < times; i++) {
            if (pages.length < pagesToShow) {
                min = Math.min.apply(null, pages);
                if (min > 1) {
                    pages.push(min - 1);
                }
            }
            if (pages.length < pagesToShow) {
                max = Math.max.apply(null, pages);
                if (max < c) {
                    pages.push(max + 1);
                }
            }
        }
        pages.sort((a, b) => a - b);
        return pages;
    }
}

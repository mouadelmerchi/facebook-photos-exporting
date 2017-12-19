import {Component, OnInit, Input} from '@angular/core';

@Component({
    selector: 'photo-details',
    templateUrl: './photo-details.component.html',
    styleUrls: ['./photo-details.component.css']
})
export class PhotoDetailsComponent implements OnInit {

    @Input() photoUri: string;

    constructor() { }

    ngOnInit() {
    }
}
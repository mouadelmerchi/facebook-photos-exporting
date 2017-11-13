import { 
    Component,
    OnInit 
}                   from '@angular/core';
import { 
    Router, 
    ActivatedRoute
}                   from '@angular/router';
import { 
    AlertService, 
    UserService 
}                   from '../services/index';

@Component({
    moduleId: module.id,
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {
    model: any = {};
    loading = false;
    returnUrl: string;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private userService: UserService,
        private alertService: AlertService
    ) { }

    ngOnInit() {
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }

    register() {
        this.loading = true;
        this.userService.create(this.model)
            .subscribe(result => {
                if (result === true) {
                    this.router.navigate([this.returnUrl]);
                } else {
                    this.alertService.error('Authentication failed');
                    this.loading = false;
                }
            }, err => {
                console.log("Status (" + err.status + ") => " + err.error.reason + ": " + err.error.body);
                this.alertService.error(err.message.body);
                this.loading = false;
            });
    }
}

import { 
    Component, 
    OnInit 
}                          from '@angular/core';
import { 
    Router, 
    ActivatedRoute 
}                          from '@angular/router';
import { 
    AlertService, 
    AuthenticationService 
}                          from '../services/index';

@Component({
    moduleId: module.id,
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    model: any = {
        email: "admin@admin.com",
        password: "admin"    
    };
    loading: boolean = false;
    returnUrl: string;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private authenticationService: AuthenticationService,
        private alertService: AlertService
    ) { }

    ngOnInit(): void {
        // reset login status
        this.authenticationService.logout();
    
        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }

    login(): void {
        this.loading = true;
        this.authenticationService.login(this.model.email, this.model.password)
            .subscribe(result => {
                if (result === true) {
                    // login successful
                   this.router.navigate([this.returnUrl]);
                } else {
                    // login failed
                    this.alertService.error('Error: Email or password is incorrect');
                    this.loading = false;
                }
            }, err => {
                console.log("Status (" + err.status + ") => " + err.error.reason + ": " + err.error.body);
                this.alertService.error(err.message.body);
                this.loading = false;
            });
    }
}
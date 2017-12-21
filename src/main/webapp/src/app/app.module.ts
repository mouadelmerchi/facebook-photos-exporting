import { NgModule }              from '@angular/core';
import { BrowserModule }         from '@angular/platform-browser';
import { FormsModule }           from '@angular/forms';
import { HttpClientModule }      from '@angular/common/http';
import {
    AppRoutingModule,
    AppInterceptorModule
}                                from './modules/index';

import { AuthGuard }             from './guards/index';
import { AlertComponent }        from './directives/index';
import {
    AlertService,
    StorageService,
    AuthenticationService,
    UserService,
    FacebookService
}                                from './services/index';

import { AppComponent }          from './app.component';
import { LoginComponent }        from './login/index';
import { RegisterComponent }     from './register/index';
import { AppHeaderComponent }    from './app-header/index';
import { AppFooterComponent }    from './app-footer/index';
import { PaginationComponent }   from './pagination/index';
import { HomeComponent }         from './home/index';
import { PhotosComponent }       from './photos/index';
import { PhotoDetailsComponent } from './photo-details/index';

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        AppRoutingModule,
        HttpClientModule,
        AppInterceptorModule
    ],
    declarations: [
        AppComponent,
        AlertComponent,
        LoginComponent,
        RegisterComponent,
        AppHeaderComponent,
        AppFooterComponent,
        PaginationComponent,
        HomeComponent,
        PhotosComponent,
        PhotoDetailsComponent
    ],
    providers: [
        AuthGuard,
        AlertService,
        StorageService,
        AuthenticationService,
        UserService,
        FacebookService
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
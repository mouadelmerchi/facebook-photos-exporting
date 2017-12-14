import { NgModule }           from '@angular/core';
import { BrowserModule }      from '@angular/platform-browser';
import { FormsModule }        from '@angular/forms';
import { HttpClientModule }   from '@angular/common/http';
import {
    AppRoutingModule,
    AppInterceptorModule
}                             from './modules/index';

import { AuthGuard }          from './guards/index';
import { AlertComponent }     from './directives/index';
import {
    AlertService,
    StorageService,
    AuthenticationService,
    UserService,
    FacebookService
}                             from './services/index';

import { AppComponent }       from './app.component';
import { LoginComponent }     from './login/index';
import { RegisterComponent }  from './register/index';
import { AppHeaderComponent } from './app-header/index';
import { AppFooterComponent } from './app-footer/index';
import { HomeComponent }      from './home/index';

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
        HomeComponent
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
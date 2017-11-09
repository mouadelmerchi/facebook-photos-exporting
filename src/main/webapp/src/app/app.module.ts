import { NgModule }       from '@angular/core';
import { BrowserModule }  from '@angular/platform-browser';
import { FormsModule }    from '@angular/forms';
import { HttpModule }     from '@angular/http';

import { AppComponent }      from './app.component';
import { AppRoutingModule }  from './app-routing.module';

import { AlertComponent }    from './directives/index';
import { AuthGuard }         from './guards/index';

import { AlertService, AuthenticationService, UserService } from './services/index';

import { HomeComponent }     from './home/index';
import { LoginComponent }    from './login/index'; 
import { RegisterComponent } from './register/index'; 

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        AppRoutingModule
    ],
    declarations: [
        AppComponent,
        AlertComponent,
        HomeComponent,
        LoginComponent,
        RegisterComponent
    ],
    providers: [
        AuthGuard,
        AlertService,
        AuthenticationService,
        UserService
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
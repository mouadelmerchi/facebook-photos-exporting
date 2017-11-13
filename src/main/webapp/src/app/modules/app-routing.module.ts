import { NgModule }          from '@angular/core';
import {
    RouterModule,
    Routes
}                            from '@angular/router';

import { AuthGuard }         from '../guards/index';
import { LoginComponent }    from '../login/index';
import { RegisterComponent } from '../register/index';
import { HomeComponent }     from '../home/index';

const routes: Routes = [
    { path: '', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },

    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
import { Injectable } from '@angular/core';

@Injectable()
export class StorageService {

    getCurrentUser(): string {
        return localStorage.getItem('currentUser');
    }

    storeCurrentUser(currentUser: string) {
        localStorage.setItem('currentUser', currentUser);
    }

    removeCurrentUser(): void {
        localStorage.removeItem('currentUser');
    }

    getToken() {
        let token: string = null;
        let currentUser = JSON.parse(this.getCurrentUser());
        if (currentUser && currentUser.token) {
            token = currentUser.token;
        }
        return token;
    }

    // Does nothing if currentUser doesn't exist
    updateToken(refreshedToken: string): void {
        let currentUser = JSON.parse(this.getCurrentUser());
        if (currentUser) {
            currentUser.token = refreshedToken;
            this.storeCurrentUser(JSON.stringify(currentUser));
        }
    }
}
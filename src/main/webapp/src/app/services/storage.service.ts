import { Injectable } from '@angular/core';

@Injectable()
export class StorageService {

    getCurrentUser(): any {
        return JSON.parse(localStorage.getItem('currentUser'));
    }

    storeCurrentUser(email: string, token: string): void {
        localStorage.setItem('currentUser', JSON.stringify({ email: email, token: token }));
    }

    removeCurrentUser(): void {
        localStorage.removeItem('currentUser');
    }

    getField(field: string): string {
        let value: string = null;
        let currentUser = this.getCurrentUser();
        if (currentUser) {
            switch (field.toLowerCase()) {
                case "email":
                    value = currentUser.email;
                    break;
                case "token":
                    value = currentUser.token;
                    break;
            }
        }
        return value;
    }

    // Does nothing if currentUser doesn't exist
    updateToken(refreshedToken: string): void {
        let currentUser = JSON.parse(this.getCurrentUser());
        if (currentUser) {
            this.storeCurrentUser(currentUser.email, refreshedToken);
        }
    }
}
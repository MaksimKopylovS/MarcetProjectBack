import { Injectable } from "@angular/core";

@Injectable({ providedIn: 'root' })
export class NotificationService{
    toasts: any[] = []

    show(message: string, options: any = {}) {
        this.toasts.push({message, ...options });
    }

    showSuccess(message: string) {
        this.show(message, { classname: 'bg-success text-light', delay: 3000 });
    }

    showFailure(message: string) {
        this.show(message, { classname: 'bg-danger text-light', delay: 2000})
    }

    remove(toast: any) {
    this.toasts = this.toasts.filter(t => t !== toast);
    }
}

import {Component, TemplateRef} from '@angular/core';

import {NotificationService} from './notification.service';

@Component({
  selector: 'notifications',
  template: `
    <ngb-toast
      *ngFor="let toast of toastService.toasts"
      [class]="toast.classname"
      [autohide]="true"
      [delay]="toast.delay || 5000"
      (hidden)="toastService.remove(toast)"
    >
      {{toast.message}}
    </ngb-toast>
  `,
  host: {'[class.ngb-toasts]': 'true'}
})
export class NotificationContainerComponent {
  constructor(public toastService: NotificationService) {}
}

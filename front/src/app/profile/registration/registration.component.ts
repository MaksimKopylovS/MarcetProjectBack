import {Component} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {NotificationService} from 'src/app/notifications/notification.service';
import {AuthService} from 'src/app/services/auth.service';

@Component({
  selector: 'profile-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {
  private username: FormControl = new FormControl('', [Validators.required, Validators.minLength(4)])
  private password: FormControl = new FormControl('', [Validators.required, Validators.minLength(8), Validators.minLength(32)])
  private repeatPassword: FormControl = new FormControl('', [Validators.required, Validators.minLength(8), Validators.minLength(32)])
  private email: FormControl = new FormControl('', [Validators.required, Validators.email])
  regForm: FormGroup

  constructor(private authService: AuthService,
              private notificationService: NotificationService,
              private formBuilder: FormBuilder,
              private activeModal: NgbActiveModal
  ) {
    this.regForm = this.formBuilder.group({
      username: this.username,
      password: this.password,
      repeatPassword: this.repeatPassword,
      email: this.email
    })
  }

  register(formValues: any) {
    this.authService.register(formValues.username, formValues.password, formValues.email)
      .subscribe(
        ok => {
          this.notificationService.showSuccess("Пользователь создан")
          this.authService.login(formValues.username, formValues.password)
            .subscribe(
              ok => {
                this.notificationService.showSuccess("Успешный вход")
                this.activeModal.close()
              },
              err => {
                this.notificationService.showFailure("Не удалось войти")
              }
            )
        },
        err => {
          this.notificationService.showFailure("Не удалось создать пользователя")
        }
      )
  }
}

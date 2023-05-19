import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {NotificationService} from 'src/app/notifications/notification.service';
import {AuthService} from 'src/app/services/auth.service';

@Component({
  selector: 'profile-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  constructor(private authService: AuthService,
              private notificationService: NotificationService,
              private activeModal: NgbActiveModal,
              private formBuilder: FormBuilder) {
  }

  private userName!: FormControl
  private password!: FormControl
  loginForm!: FormGroup

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      userName: ['', [Validators.required, Validators.minLength(4)]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(32)]]
    })
  }

  login(formValues: any) {
    this.authService.login(formValues.userName, formValues.password)
      .subscribe(
        ok => {
          this.notificationService.showSuccess("Успешный вход")
          this.activeModal.close()
        },
        err => {
          this.notificationService.showFailure("Не удалось войти")
        }
      )
  }

  validateUserName(): boolean {
    if (!this.userName) return true

    return this.userName?.valid || false
  }

  validatePassword(): boolean {
    if (!this.password) return true
    if (this.password.pristine) return true

    return this.password?.valid || false
  }

  validateAll(): boolean {
    if (this.loginForm.pristine) return false

    return this.validatePassword() &&
      this.validateUserName()
  }
}

import {Component, OnInit} from "@angular/core";
import {AuthService} from "src/app/services/auth.service";

const ADMIN_ITEMS = '/api/admin'

@Component({
  selector: "header-panel",
  templateUrl: "./header-panel.component.html",
  styleUrls: ["./header-panel.component.css"]
})
export class HeaderPanelComponent implements OnInit {
  authService: AuthService
  authorized: boolean = false
  isAdmin: boolean = false

  constructor(authService: AuthService) {
    this.authService = authService
    authService.isAuthorized.subscribe(
      ok => this.authorized = ok
    )
    authService.isAdmin.subscribe(
      ok => this.isAdmin = ok
    )
  }

  ngOnInit() {

  }

  handleLogoutButton() {
    this.authService.logout()
  }

  handleLoginButton() {
    this.authService.showModalWindow()
  }
}

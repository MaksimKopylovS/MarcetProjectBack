import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {BehaviorSubject, Observable} from "rxjs";
import {map} from "rxjs/operators";
import {UserModel} from "../models/user.model";
import {UserDtoModel} from "../models/userDto.model";
import {ProfileComponent} from "../profile/profile.component";

const HTTP_HEADERS = new HttpHeaders({'Content-Type': 'application/json'})
const AUTH_URL = '/api'
const ADMIN_ITEMS_URL = '/api/admin'

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private userSubject: BehaviorSubject<UserDtoModel>
  public user: Observable<UserDtoModel>
  private isAuthorizedSubject = new BehaviorSubject<boolean>(false)
  public isAuthorized: Observable<boolean>
  private isAuthorizedAdmin = new BehaviorSubject<boolean>(false)
  public isAdmin: Observable<boolean>

  constructor(private router: Router,
              private http: HttpClient,
              private modalService: NgbModal) {

    let userJson = localStorage.getItem('user')
    if (userJson) {
      this.userSubject = new BehaviorSubject<UserDtoModel>(JSON.parse(userJson))
      this.isAuthorizedSubject.next(true)
    } else {
      this.userSubject = new BehaviorSubject<UserDtoModel>({addressDTOList: []});
    }

    let userRole = localStorage.getItem('role')
    if (userRole === '1') {
      this.isAuthorizedAdmin.next(true)
    } else {
      this.isAuthorizedAdmin.next(false)
    }

    this.isAuthorized = this.isAuthorizedSubject.asObservable()
    this.user = this.userSubject.asObservable()
    this.isAdmin = this.isAuthorizedAdmin.asObservable()
  }

  public get userValue(): UserDtoModel {
    return this.userSubject.value;
  }

  login(username: string, password: string) {
    return this.http.post<UserDtoModel>(
      `${AUTH_URL}/auth`,
      {username, password},
      {'headers': HTTP_HEADERS}
    ).pipe(map(user => {
      localStorage.setItem('user', JSON.stringify(user));

      this.userSubject.next(user);
      this.isAuthorizedSubject.next(true)
      this.http.get(ADMIN_ITEMS_URL + '/get-all').subscribe(
        ok => {
          localStorage.setItem('role', '1')
          this.isAuthorizedAdmin.next(true)
        }
      )
      return user;
    }));
  }

  register(username: string, password: string, email: string): Observable<any> {
    let user: UserModel = {
      username: username,
      password: password,
      mail: email
    }
    return this.http.post<UserDtoModel>(AUTH_URL + '/registration', user, {'headers': HTTP_HEADERS})
  }

  logout() {
    localStorage.removeItem('user');
    localStorage.removeItem('role')
    this.userSubject.next({addressDTOList: []});
    this.router.navigate(['/store']);
    this.isAuthorizedSubject.next(false)
    this.isAuthorizedAdmin.next(false)
  }

  showModalWindow() {
    this.modalService.open(ProfileComponent)
  }
}

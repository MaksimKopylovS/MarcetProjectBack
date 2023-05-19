import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {first} from 'rxjs/operators';
import {ProductModel} from 'src/app/models/product.model';
import {AdminPanelService} from 'src/app/services/admin-panel.service';
import {FileSaverService} from 'src/app/services/file-saver.service';
import {HttpEvent, HttpHeaderResponse, HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-admin-panel-item-edit',
  templateUrl: './admin-panel-item-edit.component.html',
  styleUrls: ['./admin-panel-item-edit.component.css']
})

export class AdminPanelItemEditComponent implements OnInit {
  private item?: ProductModel
  private id?: number
  profileForm!: FormGroup
  isAddMode = false
  showSaveImage: boolean = false;
  massageImageInfo = "Сохранено успешно"
  private formData = new FormData();
  textInput: string | undefined

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private adminPanelService: AdminPanelService,
    private fileSaver: FileSaverService,
  ) {
  }

  onFileChanged(files: any) {
    let httpResponse: HttpEvent<string[]> = new HttpResponse()

    this.fileSaver.upload(this.formData).subscribe(
      event => {
        httpResponse = event
        if (httpResponse instanceof HttpHeaderResponse) {
          if (httpResponse.status == 201) {
            this.showSaveImage = true;
          } else {
            this.massageImageInfo = "ERROR"
          }
        }
      }
    )
    this.textInput = undefined
    //Сброс значение файла input на исходные параметры
    files.currentTarget.value = ''
    // location.reload() перезагрузка страницы
    this.formData = new FormData();
  }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.isAddMode = !this.id;

    // password not required in edit mode
    const passwordValidators = [Validators.minLength(6)];
    if (this.isAddMode) {
      passwordValidators.push(Validators.required);
    }

    this.profileForm = this.formBuilder.group({
      title: ['', Validators.required],
      price: ['', Validators.required],
      shortDescription: ['', Validators.required],
      fullDescription: ['', Validators.required],
      photoUrl: ['', Validators.required]
    });

    if (this.id) {
      this.adminPanelService.getItem(this.id)
        .pipe(first())
        .subscribe(item => {
          this.profileForm.patchValue(item)
          this.item = item
        });
    }
  }

  addFoto(files: any) {
    console.log("|ED")
    for (const file of files.target.files) {
      this.formData.append('files', file, file.name)
    }
    this.showSaveImage = false;
  }

  saveItem(formValues: any, files: any) {
    let model: ProductModel = {
      id: this.id,
      title: formValues.title,
      price: formValues.price,
      shortDescription: formValues.shortDescription,
      fullDescription: formValues.fullDescription,
    }
    let httpResponse: HttpEvent<string[]> = new HttpResponse()
    if (this.item?.id) {
      this.fileSaver.editProduct(model, this.formData).subscribe(
        event => {
          httpResponse = event
          if (httpResponse instanceof HttpHeaderResponse) {
            if (httpResponse.status == 201) {
              this.showSaveImage = true;
            } else {
              this.massageImageInfo = "ERROR"
            }
          }
        }
      )
    } else {
      this.fileSaver.uploadT(model, this.formData).subscribe(
        event => {
          httpResponse = event
          if (httpResponse instanceof HttpHeaderResponse) {
            if (httpResponse.status == 201) {
              this.showSaveImage = true;
            } else {
              this.massageImageInfo = "ERROR"
            }
          }
        }
      )
    }

    //Сброс значение формы input на исходные параметры
    files.currentTarget.value = ''
    // location.reload() перезагрузка страницы
    this.formData = new FormData();
  }

  cancel() {
    this.router.navigate(['admin-panel'])
  }
}



import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AdminControllerService } from '../../../../@core/services/adminController.service';
import { DeletejobEditerComponent } from '../deletejob-editer/deletejob-editer.component';
import { DialogData } from '../userlists.component';

@Component({
  selector: 'ngx-addjobeditor',
  templateUrl: './addjobeditor.component.html',
  styleUrls: ['./addjobeditor.component.scss']
})
export class AddjobeditorComponent implements OnInit {
  formAdd: FormGroup;
  public messages = '';
  constructor(private admicontrolService: AdminControllerService,
    private fb: FormBuilder,
    private router: Router,
    private _snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<DeletejobEditerComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
  ) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.formAdd = this.fb.group({
      userName: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,16}$')]],
      email: ['', [Validators.required, Validators.email]],
      newPassword: ['', [Validators.required]],
      birthDay: ['', Validators.required],
      phoneNumber: ['', Validators.pattern('^[0-9]{10}')],
      name: ['', Validators.required],
      gender: ['', Validators.required]
    });
  }
  get f() {
    return this.formAdd.controls;
  }

  addJEConfirm(event: Event) {
    event.preventDefault();
    if (this.formAdd.valid) {
      const birthday = this.formAdd.value.birthDay.split('-');
      const birthDaynew = birthday[2] + '-' + birthday[1] + '-' + birthday[0];
      this.formAdd.patchValue({ birthDay: birthDaynew });
      this.admicontrolService.createNewJE(this.formAdd.value).subscribe(
        Response => {
          if (Response.httpStatus === "OK") {
            this._snackBar.open("T???o m???i th??nh c??ng", "?????ng ??", {
              duration: 4000
            });
            this.dialogRef.close();
            this.router.navigate(['/home/userlists']);
          }
        }, error => {
          if (error.error.message === "Username ???? t???n t???i") {
            this.messages = "Username ???? t???n t???i";
          } else if (error.error.message === "Email ???? t???n t???i") {
            this.messages = "Email ???? t???n t???i";
          } else if (error.error.message === "Phone ???? t???n t???i") {
            this.messages = "Phone ???? t???n t???i";
          }
        });
    }
  }

  cancelAction(event: Event) {
    event.preventDefault();
  }
  birthday: any;
  checkkk: boolean;
  getbirthday(event: any) {
    this.birthday = event.target.value;
    this.checkkk = this.check();
    console.log(this.checkkk);
  }
  check(): boolean {
    if (Date.parse(this.birthday) < new Date().getTime()) {
      return true;
    }
    else {
      return false;
    }
  }
} 

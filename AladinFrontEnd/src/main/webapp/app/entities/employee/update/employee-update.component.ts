import { Component, ElementRef, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { Employee, IEmployee } from '../employee.model';
import { EmployeeService } from '../service/employee.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IDepartment } from 'app/entities/department/department.model';
import { DepartmentService } from 'app/entities/department/service/department.service';
import * as moment from 'moment';
import { AccountService } from '../../../core/auth/account.service';
import { ImageCroppedEvent, ImageTransform } from 'ngx-image-cropper';
import { UploadFileService } from 'app/common-services/upload-file-service/upload-file.service';

@Component({
  selector: 'jhi-employee-update',
  templateUrl: './employee-update.component.html',
  styleUrls: ['./employee-update.css'],
})
export class EmployeeUpdateComponent implements OnInit {
  isSaving = false;
  usersSharedCollection: IUser[] = [];
  departmentsSharedCollection: IDepartment[] = [];
  DateFDW = moment(new Date()).format('yyyy-MM-DD');
  imageChangedEvent: any;
  resultImage!: File;
  croppedImage!: string;
  scale = 1;
  showCropper = false;
  containWithinAspectRatio = false;
  transform: ImageTransform = {};
  displayModal!: boolean;
  canvasRotation = 0;
  selectedFile!: File;
  avatarUrl: any;
  fileName!: string;
  confirmedImage = false;
  bank = [
    {
      id: 17,
      name: 'Ngân hàng TMCP Công thương Việt Nam',
      code: 'ICB',
      bin: '970415',
      isTransfer: 1,
      short_name: 'VietinBank',
      logo: 'https://api.vietqr.io/img/ICB.3d4d6760.png',
      vietqr: 3,
    },
    {
      id: 43,
      name: 'Ngân hàng TMCP Ngoại Thương Việt Nam',
      code: 'VCB',
      bin: '970436',
      isTransfer: 1,
      short_name: 'Vietcombank',
      logo: 'https://api.vietqr.io/img/VCB.237d4924.png',
      vietqr: 3,
    },
    {
      id: 21,
      name: 'Ngân hàng TMCP Quân đội',
      code: 'MB',
      bin: '970422',
      isTransfer: 1,
      short_name: 'MBBank',
      logo: 'https://api.vietqr.io/img/MB.f9740319.png',
      vietqr: 3,
    },
    {
      id: 2,
      name: 'Ngân hàng TMCP Á Châu',
      code: 'ACB',
      bin: '970416',
      isTransfer: 1,
      short_name: 'ACB',
      logo: 'https://api.vietqr.io/img/ACB.6e7fe025.png',
      vietqr: 3,
    },
    {
      id: 47,
      name: 'Ngân hàng TMCP Việt Nam Thịnh Vượng',
      code: 'VPB',
      bin: '970432',
      isTransfer: 1,
      short_name: 'VPBank',
      logo: 'https://api.vietqr.io/img/VPB.ca2e7350.png',
      vietqr: 3,
    },
    {
      id: 39,
      name: 'Ngân hàng TMCP Tiên Phong',
      code: 'TPB',
      bin: '970423',
      isTransfer: 1,
      short_name: 'TPBank',
      logo: 'https://api.vietqr.io/img/TPB.883b6135.png',
      vietqr: 3,
    },
    {
      id: 22,
      name: 'Ngân hàng TMCP Hàng Hải',
      code: 'MSB',
      bin: '970426',
      isTransfer: 1,
      short_name: 'MSB',
      logo: 'https://api.vietqr.io/img/MSB.1b076e2a.png',
      vietqr: 3,
    },
    {
      id: 23,
      name: 'Ngân hàng TMCP Nam Á',
      code: 'NAB',
      bin: '970428',
      isTransfer: 1,
      short_name: 'NamABank',
      logo: 'https://api.vietqr.io/img/NAB.f74b0fa8.png',
      vietqr: 3,
    },
    {
      id: 20,
      name: 'Ngân hàng TMCP Bưu Điện Liên Việt',
      code: 'LPB',
      bin: '970449',
      isTransfer: 1,
      short_name: 'LienVietPostBank',
      logo: 'https://api.vietqr.io/img/LPB.07a7c83b.png',
      vietqr: 3,
    },
    {
      id: 44,
      name: 'Ngân hàng TMCP Bản Việt',
      code: 'VCCB',
      bin: '970454',
      isTransfer: 1,
      short_name: 'VietCapitalBank',
      logo: 'https://api.vietqr.io/img/VCCB.654a3506.png',
      vietqr: 3,
    },
    {
      id: 4,
      name: 'Ngân hàng TMCP Đầu tư và Phát triển Việt Nam',
      code: 'BIDV',
      bin: '970418',
      isTransfer: 1,
      short_name: 'BIDV',
      logo: 'https://api.vietqr.io/img/BIDV.862fd58b.png',
      vietqr: 3,
    },
    {
      id: 36,
      name: 'Ngân hàng TMCP Sài Gòn Thương Tín',
      code: 'STB',
      bin: '970403',
      isTransfer: 1,
      short_name: 'Sacombank',
      logo: 'https://api.vietqr.io/img/STB.a03fef2c.png',
      vietqr: 3,
    },
    {
      id: 45,
      name: 'Ngân hàng TMCP Quốc tế Việt Nam',
      code: 'VIB',
      bin: '970441',
      isTransfer: 1,
      short_name: 'VIB',
      logo: 'https://api.vietqr.io/img/VIB.4ecb28e6.png',
      vietqr: 3,
    },
    {
      id: 12,
      name: 'Ngân hàng TMCP Phát triển Thành phố Hồ Chí Minh',
      code: 'HDB',
      bin: '970437',
      isTransfer: 1,
      short_name: 'HDBank',
      logo: 'https://api.vietqr.io/img/HDB.4256e826.png',
      vietqr: 3,
    },
    {
      id: 33,
      name: 'Ngân hàng TMCP Đông Nam Á',
      code: 'SEAB',
      bin: '970440',
      isTransfer: 1,
      short_name: 'SeABank',
      logo: 'https://api.vietqr.io/img/SEAB.1864a665.png',
      vietqr: 3,
    },
    {
      id: 11,
      name: 'Ngân hàng Thương mại TNHH MTV Dầu Khí Toàn Cầu',
      code: 'GPB',
      bin: '970408',
      isTransfer: 0,
      short_name: 'GPBank',
      logo: 'https://api.vietqr.io/img/GPB.29bd127d.png',
      vietqr: 1,
    },
    {
      id: 30,
      name: 'Ngân hàng TMCP Đại Chúng Việt Nam',
      code: 'PVCB',
      bin: '970412',
      isTransfer: 1,
      short_name: 'PVcomBank',
      logo: 'https://api.vietqr.io/img/PVCB.6129f342.png',
      vietqr: 3,
    },
    {
      id: 24,
      name: 'Ngân hàng TMCP Quốc Dân',
      code: 'NCB',
      bin: '970419',
      isTransfer: 1,
      short_name: 'NCB',
      logo: 'https://api.vietqr.io/img/NCB.7d8af057.png',
      vietqr: 3,
    },
    {
      id: 37,
      name: 'Ngân hàng TNHH MTV Shinhan Việt Nam',
      code: 'SHBVN',
      bin: '970424',
      isTransfer: 1,
      short_name: 'ShinhanBank',
      logo: 'https://api.vietqr.io/img/SHBVN.b6c0e806.png',
      vietqr: 3,
    },
    {
      id: 31,
      name: 'Ngân hàng TMCP Sài Gòn',
      code: 'SCB',
      bin: '970429',
      isTransfer: 1,
      short_name: 'SCB',
      logo: 'https://api.vietqr.io/img/SCB.5ca4bec4.png',
      vietqr: 3,
    },
    {
      id: 29,
      name: 'Ngân hàng TMCP Xăng dầu Petrolimex',
      code: 'PGB',
      bin: '970430',
      isTransfer: 1,
      short_name: 'PGBank',
      logo: 'https://api.vietqr.io/img/PGB.825cbbda.png',
      vietqr: 3,
    },
    {
      id: 42,
      name: 'Ngân hàng Nông nghiệp và Phát triển Nông thôn Việt Nam',
      code: 'VBA',
      bin: '970405',
      isTransfer: 0,
      short_name: 'Agribank',
      logo: 'https://api.vietqr.io/img/VBA.d72a0e06.png',
      vietqr: 0,
    },
    {
      id: 38,
      name: 'Ngân hàng TMCP Kỹ thương Việt Nam',
      code: 'TCB',
      bin: '970407',
      isTransfer: 1,
      short_name: 'Techcombank',
      logo: 'https://api.vietqr.io/img/TCB.b2828982.png',
      vietqr: 3,
    },
    {
      id: 34,
      name: 'Ngân hàng TMCP Sài Gòn Công Thương',
      code: 'SGICB',
      bin: '970400',
      isTransfer: 1,
      short_name: 'SaigonBank',
      logo: 'https://api.vietqr.io/img/SGICB.5886546f.png',
      vietqr: 3,
    },
    {
      id: 9,
      name: 'Ngân hàng TMCP Đông Á',
      code: 'DOB',
      bin: '970406',
      isTransfer: 0,
      short_name: 'DongABank',
      logo: 'https://api.vietqr.io/img/DOB.92bbf6f4.png',
      vietqr: 0,
    },
    {
      id: 3,
      name: 'Ngân hàng TMCP Bắc Á',
      code: 'BAB',
      bin: '970409',
      isTransfer: 0,
      short_name: 'BacABank',
      logo: 'https://api.vietqr.io/img/BAB.75c3a8c2.png',
      vietqr: 0,
    },
    {
      id: 32,
      name: 'Ngân hàng TNHH MTV Standard Chartered Bank Việt Nam',
      code: 'SCVN',
      bin: '970410',
      isTransfer: 0,
      short_name: 'StandardChartered',
      logo: 'https://api.vietqr.io/img/SCVN.a53976be.png',
      vietqr: 0,
    },
    {
      id: 27,
      name: 'Ngân hàng Thương mại TNHH MTV Đại Dương',
      code: 'Oceanbank',
      bin: '970414',
      isTransfer: 0,
      short_name: 'Oceanbank',
      logo: 'https://api.vietqr.io/img/OCEANBANK.f84c3119.png',
      vietqr: 0,
    },
    {
      id: 48,
      name: 'Ngân hàng Liên doanh Việt - Nga',
      code: 'VRB',
      bin: '970421',
      isTransfer: 0,
      short_name: 'VRB',
      logo: 'https://api.vietqr.io/img/VRB.9d6d40f3.png',
      vietqr: 0,
    },
    {
      id: 1,
      name: 'Ngân hàng TMCP An Bình',
      code: 'ABB',
      bin: '970425',
      isTransfer: 0,
      short_name: 'ABBANK',
      logo: 'https://api.vietqr.io/img/ABB.9defb03d.png',
      vietqr: 0,
    },
    {
      id: 41,
      name: 'Ngân hàng TMCP Việt Á',
      code: 'VAB',
      bin: '970427',
      isTransfer: 0,
      short_name: 'VietABank',
      logo: 'https://api.vietqr.io/img/VAB.9bf85d8e.png',
      vietqr: 0,
    },
    {
      id: 10,
      name: 'Ngân hàng TMCP Xuất Nhập khẩu Việt Nam',
      code: 'EIB',
      bin: '970431',
      isTransfer: 0,
      short_name: 'Eximbank',
      logo: 'https://api.vietqr.io/img/EIB.ae2f0252.png',
      vietqr: 0,
    },
    {
      id: 46,
      name: 'Ngân hàng TMCP Việt Nam Thương Tín',
      code: 'VIETBANK',
      bin: '970433',
      isTransfer: 1,
      short_name: 'VietBank',
      logo: 'https://api.vietqr.io/img/VIETBANK.bb702d50.png',
      vietqr: 3,
    },
    {
      id: 18,
      name: 'Ngân hàng TNHH Indovina',
      code: 'IVB',
      bin: '970434',
      isTransfer: 0,
      short_name: 'IndovinaBank',
      logo: 'https://api.vietqr.io/img/IVB.ee79782c.png',
      vietqr: 0,
    },
    {
      id: 5,
      name: 'Ngân hàng TMCP Bảo Việt',
      code: 'BVB',
      bin: '970438',
      isTransfer: 1,
      short_name: 'BaoVietBank',
      logo: 'https://api.vietqr.io/img/BVB.2b7aab15.png',
      vietqr: 3,
    },
    {
      id: 28,
      name: 'Ngân hàng TNHH MTV Public Việt Nam',
      code: 'PBVN',
      bin: '970439',
      isTransfer: 0,
      short_name: 'PublicBank',
      logo: 'https://api.vietqr.io/img/PBVN.67dbc9af.png',
      vietqr: 0,
    },
    {
      id: 35,
      name: 'Ngân hàng TMCP Sài Gòn - Hà Nội',
      code: 'SHB',
      bin: '970443',
      isTransfer: 0,
      short_name: 'SHB',
      logo: 'https://api.vietqr.io/img/SHB.665daa27.png',
      vietqr: 0,
    },
    {
      id: 6,
      name: 'Ngân hàng Thương mại TNHH MTV Xây dựng Việt Nam',
      code: 'CBB',
      bin: '970444',
      isTransfer: 0,
      short_name: 'CBBank',
      logo: 'https://api.vietqr.io/img/CBB.5b47e56f.png',
      vietqr: 0,
    },
    {
      id: 26,
      name: 'Ngân hàng TMCP Phương Đông',
      code: 'OCB',
      bin: '970448',
      isTransfer: 1,
      short_name: 'OCB',
      logo: 'https://api.vietqr.io/img/OCB.84d922d1.png',
      vietqr: 3,
    },
    {
      id: 19,
      name: 'Ngân hàng TMCP Kiên Long',
      code: 'KLB',
      bin: '970452',
      isTransfer: 1,
      short_name: 'KienLongBank',
      logo: 'https://api.vietqr.io/img/KLB.23902895.png',
      vietqr: 3,
    },
    {
      id: 7,
      name: 'Ngân hàng TNHH MTV CIMB Việt Nam',
      code: 'CIMB',
      bin: '422589',
      isTransfer: 0,
      short_name: 'CIMB',
      logo: 'https://api.vietqr.io/img/CIMB.70b35f80.png',
      vietqr: 0,
    },
    {
      id: 14,
      name: 'Ngân hàng TNHH MTV HSBC (Việt Nam)',
      code: 'HSBC',
      bin: '458761',
      isTransfer: 0,
      short_name: 'HSBC',
      logo: 'https://api.vietqr.io/img/HSBC.6fa79196.png',
      vietqr: 0,
    },
    {
      id: 8,
      name: 'DBS Bank Ltd - Chi nhánh Thành phố Hồ Chí Minh',
      code: 'DBS',
      bin: '796500',
      isTransfer: 0,
      short_name: 'DBSBank',
      logo: 'https://api.vietqr.io/img/DBS.83742b1e.png',
      vietqr: 0,
    },
    {
      id: 25,
      name: 'Ngân hàng Nonghyup - Chi nhánh Hà Nội',
      code: 'NHB HN',
      bin: '801011',
      isTransfer: 0,
      short_name: 'Nonghyup',
      logo: 'https://api.vietqr.io/img/NHB%20HN.6a3f7952.png',
      vietqr: 0,
    },
    {
      id: 13,
      name: 'Ngân hàng TNHH MTV Hong Leong Việt Nam',
      code: 'HLBVN',
      bin: '970442',
      isTransfer: 0,
      short_name: 'HongLeong',
      logo: 'https://api.vietqr.io/img/HLBVN.4a284a9a.png',
      vietqr: 0,
    },
    {
      id: 15,
      name: 'Ngân hàng Công nghiệp Hàn Quốc - Chi nhánh Hà Nội',
      code: 'IBK - HN',
      bin: '970455',
      isTransfer: 0,
      short_name: 'IBK Bank',
      logo: 'https://api.vietqr.io/img/IBK%20-%20HN.eee4e569.png',
      vietqr: 0,
    },
    {
      id: 16,
      name: 'Ngân hàng Công nghiệp Hàn Quốc - Chi nhánh TP. Hồ Chí Minh',
      code: 'IBK - HCM',
      bin: '970456',
      isTransfer: 0,
      short_name: 'IBK Bank',
      logo: 'https://api.vietqr.io/img/IBK%20-%20HN.eee4e569.png',
      vietqr: 0,
    },
    {
      id: 49,
      name: 'Ngân hàng TNHH MTV Woori Việt Nam',
      code: 'WVN',
      bin: '970457',
      isTransfer: 0,
      short_name: 'Woori',
      logo: 'https://api.vietqr.io/img/WVN.45451999.png',
      vietqr: 0,
    },
    {
      id: 40,
      name: 'Ngân hàng United Overseas - Chi nhánh TP. Hồ Chí Minh',
      code: 'UOB',
      bin: '970458',
      isTransfer: 0,
      short_name: 'UnitedOverseas',
      logo: 'https://api.vietqr.io/img/UOB.e6a847d2.png',
      vietqr: 0,
    },
    {
      id: 50,
      name: 'Ngân hàng Kookmin - Chi nhánh Hà Nội',
      code: 'KBHN',
      bin: '970462',
      isTransfer: 0,
      short_name: 'KookminHN',
      logo: 'https://api.vietqr.io/img/KBHN.5126abce.png',
      vietqr: 0,
    },
    {
      id: 51,
      name: 'Ngân hàng Kookmin - Chi nhánh Thành phố Hồ Chí Minh',
      code: 'KBHCM',
      bin: '970463',
      isTransfer: 0,
      short_name: 'KookminHCM',
      logo: 'https://api.vietqr.io/img/KBHN.5126abce.png',
      vietqr: 0,
    },
    {
      id: 52,
      name: 'Ngân hàng Hợp tác xã Việt Nam',
      code: 'COOPBANK',
      bin: '970446',
      isTransfer: 0,
      short_name: 'COOPBANK',
      logo: 'https://api.vietqr.io/img/COOPBANK.16fc2602.png',
      vietqr: 0,
    },
  ];

  editForm = this.fb.group({
    id: [],
    first_day_work: [null, [Validators.required]],
    full_name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(2000)]],
    phone_number: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(32), Validators.pattern(/^-?(|[0-9]\d*)?$/)]],
    email: [
      null,
      [
        Validators.required,
        Validators.maxLength(30),
        Validators.email
      ],
    ],
    date_of_birth: [null, [Validators.required]],
    countryside: [null, [Validators.required, Validators.maxLength(2000)]],
    current_residence: [null, [Validators.required, Validators.maxLength(2000)]],
    relative: [null, [Validators.required, Validators.maxLength(2000)]],
    favourite: [null, [Validators.required, Validators.maxLength(2000)]],
    education: [null, [Validators.required, Validators.maxLength(2000)]],
    experience: [null, [Validators.required, Validators.maxLength(2000)]],
    english: [null, [Validators.required, Validators.maxLength(2000)]],
    objective_in_cv: [null, [Validators.required, Validators.maxLength(2000)]],
    marital_status: [null, [Validators.required, Validators.maxLength(2000)]],
    children: [null, [Validators.required, Validators.maxLength(2000)]],
    family: [null, [Validators.required, Validators.maxLength(2000)]],
    gender: [null, [Validators.required, Validators.maxLength(10)]],
    avatar: [],
    certification: [],
    certificationContentType: [],
    certificationFile:[],
    bank_name: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(200)]],
    account_number: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(30), Validators.pattern(/^-?(|[0-9]\d*)?$/)]],
    user: [null, Validators.required],
    department: [null, Validators.required],
  });
  defaultAvatar!: string;

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected employeeService: EmployeeService,
    protected userService: UserService,
    protected departmentService: DepartmentService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    private uploadFileService: UploadFileService
  ) {}

  ngOnInit(): void {
    this.loadRelationshipsOptions();
  }

  showModalDialog(): void {
    this.displayModal = true;
  }

  hideModalDialog(): void {
    this.displayModal = false;
  }

  confirmImage(): void {
    this.displayModal = false;
    this.confirmedImage = true;
  }

  declineImage(): void {
    this.displayModal = false;
    this.editForm.patchValue({
      avatar: null,
    });
    this.croppedImage = this.defaultAvatar;
    this.showCropper = false;
  }

  fileChangeEvent(event: any): void {
    const fileType = event.target.files[0].type;
    console.log(123,fileType);
    if (fileType.toLowerCase() !== 'image/jpeg' && fileType.toLowerCase() !== 'image/png'){
      this.editForm.controls['avatar'].setErrors({'invalidFile':true});
      this.showCropper = false;
    } else {
      this.fileName = event.target.files[0].name;
      this.imageChangedEvent = event;
      this.confirmedImage = false;
    }
  }

  base64ToFile(data: any, filename: string): File {
    const arr = data.split(',');
    const mime = arr[0].match(/:(.*?);/)[1];
    const bstr = atob(arr[1]);
    let n = bstr.length;
    const u8arr = new Uint8Array(n);
    while (n--) {
      u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], filename, { type: mime });
  }

  imageCropped(event: ImageCroppedEvent): void {
    this.resultImage = this.base64ToFile(event.base64 as string, this.fileName);
    this.croppedImage = event.base64 as string;
    this.confirmedImage = false;
  }

  imageLoaded(): void {
    this.showCropper = true;
  }

  loadImageFailed(): void {
    console.log('Load failed');
  }

  resetImage(): void {
    this.scale = 1;
    this.transform = {};
  }

  zoomOut(): void {
    this.scale -= 0.1;
    this.transform = {
      ...this.transform,
      scale: this.scale,
    };
  }

  zoomIn(): void {
    this.scale += 0.1;
    this.transform = {
      ...this.transform,
      scale: this.scale,
    };
  }

  rotateLeft(): void {
    this.canvasRotation--;
    this.flipAfterRotate();
  }

  rotateRight(): void {
    this.canvasRotation++;
    this.flipAfterRotate();
  }

  toggleContainWithinAspectRatio(): void {
    this.containWithinAspectRatio = !this.containWithinAspectRatio;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    const fileType = event.target.files[0].type;
    if (fileType === 'application/pdf'){
      this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
        error: (err: FileLoadError) =>
          this.eventManager.broadcast(new EventWithContent<AlertError>('AladinTechApp.error', { ...err, key: 'error.file.' + err.key })),
      });
    } else {
      this.editForm.controls['certificationFile'].setErrors({'invalidPdfFile':true});
    }
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employee = this.createFromForm();
    if (this.croppedImage !== this.defaultAvatar) {
      this.uploadFileService.uploadFile(this.resultImage).subscribe(res => {
        employee.avatar = res;
        this.subscribeToSaveResponse(this.employeeService.update(employee));
      });
    } else {
      employee.avatar = this.defaultAvatar;
      this.subscribeToSaveResponse(this.employeeService.update(employee));
    }
  }

  trackUserById(index: number, item: IUser): number {
    return item.id!;
  }

  trackDepartmentById(index: number, item: IDepartment): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(employee: IEmployee): void {
    this.editForm.patchValue({
      id: employee.id,
      first_day_work: employee.first_day_work,
      full_name: employee.full_name,
      phone_number: employee.phone_number,
      email: employee.email,
      date_of_birth: employee.date_of_birth,
      countryside: employee.countryside,
      current_residence: employee.current_residence,
      relative: employee.relative,
      favourite: employee.favourite,
      education: employee.education,
      experience: employee.experience,
      english: employee.english,
      objective_in_cv: employee.objective_in_cv,
      marital_status: employee.marital_status,
      children: employee.children,
      family: employee.family,
      gender: employee.gender,
      certification: employee.certification,
      certificationContentType: employee.certificationContentType,
      bank_name: employee.bank_name,
      account_number: employee.account_number,
      user: employee.user?.login,
      department: employee.department?.departmentName,
    });
    this.croppedImage = employee.avatar as string;
    this.defaultAvatar = employee.avatar as string;
    this.confirmedImage = true;
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, employee.user);
    this.departmentsSharedCollection = this.departmentService.addDepartmentToCollectionIfMissing(
      this.departmentsSharedCollection,
      employee.department
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => {
        this.usersSharedCollection = users;
        this.activatedRoute.data.subscribe(({ employee }) => {
          this.updateForm(employee);
        });
      });

    this.departmentService
      .query()
      .pipe(map((res: HttpResponse<IDepartment[]>) => res.body ?? []))
      .pipe(
        map((departments: IDepartment[]) =>
          this.departmentService.addDepartmentToCollectionIfMissing(departments, this.editForm.get('department')!.value)
        )
      )
      .subscribe((departments: IDepartment[]) => {
        this.departmentsSharedCollection = departments;
        this.activatedRoute.data.subscribe(({ employee }) => {
          this.updateForm(employee);
        });
      });
  }

  protected createFromForm(): IEmployee {
    return {
      ...new Employee(),
      id: this.editForm.get(['id'])!.value,
      first_day_work: this.editForm.get(['first_day_work'])!.value,
      full_name: this.editForm.get(['full_name'])!.value,
      phone_number: this.editForm.get(['phone_number'])!.value,
      email: this.editForm.get(['email'])!.value,
      date_of_birth: this.editForm.get(['date_of_birth'])!.value,
      countryside: this.editForm.get(['countryside'])!.value,
      current_residence: this.editForm.get(['current_residence'])!.value,
      relative: this.editForm.get(['relative'])!.value,
      favourite: this.editForm.get(['favourite'])!.value,
      education: this.editForm.get(['education'])!.value,
      experience: this.editForm.get(['experience'])!.value,
      english: this.editForm.get(['english'])!.value,
      objective_in_cv: this.editForm.get(['objective_in_cv'])!.value,
      marital_status: this.editForm.get(['marital_status'])!.value,
      children: this.editForm.get(['children'])!.value,
      family: this.editForm.get(['family'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      certificationContentType: this.editForm.get(['certificationContentType'])!.value,
      certification: this.editForm.get(['certification'])!.value,
      bank_name: this.editForm.get(['bank_name'])!.value,
      account_number: this.editForm.get(['account_number'])!.value,
      user: this.usersSharedCollection[0],
      department: this.departmentsSharedCollection[0],
    };
  }
  private flipAfterRotate(): void {
    const flippedH = this.transform.flipH;
    const flippedV = this.transform.flipV;
    this.transform = {
      ...this.transform,
      flipH: flippedV,
      flipV: flippedH,
    };
  }
}

import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { FormBuilder, Validators, FormGroup, FormArray, FormControl } from '@angular/forms';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ContactPageService } from './contact-page.service';
import { IService, ServiceModel } from '../../service/service.model';
import { toString } from '@ng-bootstrap/ng-bootstrap/util/util';

@Component({
  selector: 'jhi-contact-page',
  templateUrl: './contact-page.component.html',
  styleUrls: ['./contact-page.component.scss'],
})
export class ContactPageComponent implements OnInit {
  listService: IService[] | null = null;
  totalItems = 0;
  // doNotMatch = false;
  error = false;
  test: any;
  // errorEmailExists = false;
  // errorUserExists = false;
  success = false;
  dialCode: any;
  dialName: any;
  dialIso2: any;
  obj: any;
  number: any;

  contactForm = this.fb.group({
    fullname: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(254)]],
    email: [
      '',
      [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(254),
        Validators.email,
        Validators.pattern('^[A-Za-z]+[0-9A-Za-z+_.%]*@[0-9A-Za-z.-]+(\\.[A-Za-z]{2,4}){1,2}$'),
      ],
    ],
    phone: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(10)]],
    messages: ['', [Validators.required, Validators.minLength(4)]],
    job: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254)]],
    company: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254)]],
    interestThing: new FormArray([]),
    receive: true,
  });

  constructor(private translateService: TranslateService, private contactPageService: ContactPageService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.contactPageService.query().subscribe(
      (res: HttpResponse<IService[]>) => {
        this.totalItems = Number(res.headers.get('X-Total-Count'));
        this.onSuccess(res.body, res.headers);
        this.addCheckboxes();
      },
      () => (this.totalItems = -1)
    );
  }
  //phone ng2telInput
  telInputObject(obj: any): void {
    obj.setCountry('vn');
    this.obj = obj;
  }

  numericOnly(event: any): any {
    const patt = /^[0-9]$/;
    const result = patt.test(event.key);
    return result;
  }

  onCountryChange(event: any): void {
    this.dialCode = event.dialCode;
    this.dialName = event.name;
    this.dialIso2 = event.iso2;
  }
  //--------------------------------------

  sendContact(): void {
    this.error = false;
    const interestThing = this.contactForm.value.interestThing.map((checked: any, i: number) =>
      String(checked && this.listService != null ? this.listService[i].type : '')
    );
    let phoneTemp = String(this.contactForm.get(['phone'])!.value);
    if (this.dialCode === '84' && phoneTemp.startsWith('0')) {
      phoneTemp = phoneTemp.substring(1);
    }
    // const receiveMail = (this.contactForm.get('receive')!.value ?', Receive mail': '');
    this.test = interestThing.toString();
    const interest = interestThing.toString();
    const message = this.contactForm.get(['messages'])!.value;
    const name = this.contactForm.get(['fullname'])!.value;
    const email = this.contactForm.get(['email'])!.value;
    const phone = String(this.dialCode) + phoneTemp;
    const jobtitle = this.contactForm.get(['job'])!.value;
    const company = this.contactForm.get(['company'])!.value;
    this.contactPageService.create({ name, email, message, phone, jobtitle, company, interest }).subscribe(
      () => (this.success = true),
      response => this.processError(response)
    );
  }

  noWhitespaceValidator(control: FormControl): any {
    const isWhitespace = (control.value || '').trim().length === 0;
    this.test = 1;
    const isValid = !isWhitespace;
    return isValid ? null : { whitespace: true };
  }

  get ordersFormArray(): FormArray {
    return this.contactForm.controls.interestThing as FormArray;
  }

  private addCheckboxes(): any {
    this.listService?.forEach(() => this.ordersFormArray.push(new FormControl(false)));
  }

  private processError(response: HttpErrorResponse): void {
    this.error = true;
    this.test = response;
  }

  private onSuccess(list: IService[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.listService = list!.sort();
  }
}

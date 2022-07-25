import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, OnInit, SimpleChange, TemplateRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import {
  faBookOpen,
  faCalendar,
  faCaretRight,
  faCheck,
  faDollarSign,
  faFemale,
  faFlag,
  faLevelUpAlt,
  faMale,
  faMapMarkedAlt,
  faSadCry,
  faSearch,
} from '@fortawesome/free-solid-svg-icons';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateService } from '@ngx-translate/core';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { combineLatest } from 'rxjs';
import { FromEventTarget } from 'rxjs/internal/observable/fromEvent';
import { ASC, DESC, SORT } from '../../config/pagination.constants';
import { IRecruitmen } from '../../entities/recruitmen/recruitmen.model';
import { CareerService } from '../career.service';
import * as moment from 'moment';

interface HTMLInputEvent extends Event {
  target: HTMLInputElement & EventTarget;
}

@Component({
  selector: 'jhi-career-list',
  templateUrl: './career-list.component.html',
  styleUrls: ['./career-list.component.scss'],
})
export class CareerListComponent implements OnInit {
  // icon
  faDollarSign = faDollarSign;
  faMapMarkedAlt = faMapMarkedAlt;
  faMale = faMale;
  faFemale = faFemale;
  faCalender = faCalendar;
  faLevelUpAlt = faLevelUpAlt;
  faCaretRight = faCaretRight;
  faBookOpen = faBookOpen;
  faSearch = faSearch;
  faFlag = faFlag;
  faCheck = faCheck;
  faSadCry = faSadCry;
  DateFDW = moment(new Date()).format('yyyy-MM-DD');

  // job
  recruitment?: IRecruitmen[] = [];

  // modal
  closeResult = '';

  // data
  isLoading = false;
  totalItems = 0;
  itemsPerPage = 10;
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  searchKey = '';
  recruitmentSelected?: IRecruitmen;

  contentType = '';
  //request
  reqLoading = false;

  //utils
  dataUti = new DataUtils();

  // file
  cvFile?: string;

  // bucket
  bucket: Record<string, any> = {};

  //translate
  candidate: Record<string, any> = {
    placeholder: {},
  };

  //success form
  formValid = false;

  // search form
  searchForm: FormGroup = this.formBuilder.group({
    searchKey: [''],
  });

  // submit recruitment form
  submitRecruitment: FormGroup = this.formBuilder.group({
    birthday: ['', [Validators.required, Validators.maxLength(254), Validators.minLength(1)]],
    cv: ['', [Validators.required, Validators.maxLength(254), Validators.minLength(1)]],
    // cvContentType: '',
    // dateRegister: ['', [Validators.required, Validators.maxLength(254), Validators.minLength(1)]],
    education: ['', [Validators.required, Validators.maxLength(254), Validators.minLength(1)]],
    email: [
      '',
      [
        Validators.required,
        Validators.maxLength(254),
        Validators.minLength(5),
        Validators.email,
        Validators.pattern('^[A-Za-z]+[0-9A-Za-z+_.%]*@[0-9A-Za-z.-]+(\\.[A-Za-z]{2,4}){1,2}$'),
      ],
    ],
    experience: ['', [Validators.required, Validators.maxLength(254), Validators.minLength(1)]],
    fullname: ['', [Validators.required, Validators.maxLength(254), Validators.minLength(1)]],
    // id: 0,
    location: ['', [Validators.required, Validators.maxLength(254), Validators.minLength(1)]],
    phone: [null, [Validators.required, Validators.maxLength(254), Validators.minLength(10)]],
    // position: ['', [Validators.required, Validators.maxLength(254), Validators.minLength(1)]],
    preference: ['', [Validators.required, Validators.maxLength(254), Validators.minLength(1)]],
    relationship: ['alone', [Validators.required]],
    sex: ['female', [Validators.required]],
    target: ['', [Validators.required, Validators.maxLength(254), Validators.minLength(1)]],
  });

  constructor(
    protected careerService: CareerService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
    private formBuilder: FormBuilder,
    private translateService: TranslateService,
    private dataUtils: DataUtils,
    private eventManager: EventManager
  ) {}

  ngOnInit(): void {
    this.handleNavigation();
    this.translateService.get('candidate').subscribe(data => {
      this.candidate = data;
    });

    // convert phone into number
    this.submitRecruitment.valueChanges.subscribe(
      data => {
        this.contentType = data.cv ? `application/${String(data.cv.split('.')[1])}` : '';
        this.formValid = this.submitRecruitment.valid;
      },
      err => {
        console.log(err);
      }
    );

    //
  }

  ngOnChanges(changes: SimpleChange): void {
    //Called before any other lifecycle hook. Use it to inject dependencies, but avoid any serious work here.
    //Add '${implements OnChanges}' to the class.
    console.log(changes.currentValue);
    const fileReader = new FileReader();
  }
  // modal action
  open(content: TemplateRef<HTMLButtonElement>, ariaLabelledBy: string, id?: string | number): void {
    this.recruitmentSelected = this.recruitment?.filter(item => item.id === id)[0];
    this.modalService.open(content, { ariaLabelledBy, centered: true }).result.then(
      (result: string) => {
        this.closeResult = `Closed with: ${result}`;
      },
      (reason: ModalDismissReasons) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      }
    );
  }

  // upload cv
  getDismissReason(reason: number): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  // moving into career detail
  gotoJobDetail(id?: number): void {
    id && this.router.navigateByUrl(`/career/${id}`);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.submitRecruitment, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('AladinTechApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  // search submit
  onSubmit(e?: FromEventTarget<any>): void {
    this.router.navigate(['/career'], {
      queryParams: { page: this.page, itemsPerPage: this.itemsPerPage, searchKey: this.searchForm.value.searchKey },
      queryParamsHandling: 'merge',
    });
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 1;
    const s = this.sort();
    this.careerService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: s,
      })
      .subscribe(
        (res: HttpResponse<IRecruitmen[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
        },
        () => {
          this.isLoading = false;
          this.onError();
        }
      );
  }

  navigateToContact(): void {
    this.router.navigateByUrl('/contacts');
  }
  // on recruitment
  onRecruitment(): void {
    const body = { ...this.submitRecruitment.getRawValue() };
    console.log(body, this.submitRecruitment.valid);
    if (this.formValid) {
      Object.keys(body).forEach(k => {
        if (typeof body[k] === 'string') {
          body[k].trim();
        }
      });

      const d = new Date();

      const new_body: IRecruitmen = {
        ...body,
        birthday: body.birthday,
        recruitment: this.recruitmentSelected,
        cvContentType: this.contentType,
        dateRegister: d,
        position: this.recruitmentSelected?.position,
      };
      this.reqLoading = true;

      this.careerService.recruitment(new_body).subscribe(
        data => {
          this.reqLoading = false;
          this.modalService.dismissAll('Success request');
        },
        err => {
          this.reqLoading = false;
          console.log(err);
        }
      );
    }
  }

  trackId(index: number, item: IRecruitmen): number {
    return item.id!;
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? DESC : ASC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get(SORT) ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === ASC;
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    });
  }

  protected onSuccess(data: IRecruitmen[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/career'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? DESC : ASC),
        },
      });
    }
    this.recruitment = data ?? [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}

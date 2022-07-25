import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, TemplateRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { faCaretRight, faShare, faCalendar, faCheck, faSpinner } from '@fortawesome/free-solid-svg-icons';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateService } from '@ngx-translate/core';
import { AlertService } from 'app/core/util/alert.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { IRecruitmen } from 'app/entities/recruitmen/recruitmen.model';
import { AlertError } from 'app/shared/alert/alert-error.model';
import * as dayjs from 'dayjs';
import { IProduct } from '../../product/product.model';
import { ICareer } from '../career.model';
import { CareerService } from '../career.service';
import * as moment from 'moment';
@Component({
  selector: 'jhi-career-detail',
  templateUrl: './career-detail.component.html',
  styleUrls: ['./career-detail.component.scss'],
})
export class CareerDetailComponent implements OnInit {
  faCaretRight = faCaretRight;
  faShare = faShare;
  recruitmentId: any;
  isLoading = false;
  faCheck = faCheck;
  faCalendar = faCalendar;
  faSpinner = faSpinner;
  DateFDW = moment(new Date()).format('yyyy-MM-DD');
  careerDetail: IRecruitmen | null = {
    duration: null,
    description:
      "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum",
  };
  //success form
  formValid = false;
  reqLoading = false;
  duration?: string = '';
  careerMoreJob?: IRecruitmen[] = [];

  //hieumt
  listDescription: string[] | undefined = undefined;
  listRequire: string[] | undefined = undefined;
  listBenefit: string[] | undefined = undefined;

  closeResult = '';
  // data
  candidate: Record<string, any> = {
    placeholder: {},
  };

  contentType = '';

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
    phone: [null, [Validators.required, Validators.maxLength(254), Validators.minLength(1)]],
    // position: ['', [Validators.required, Validators.maxLength(254), Validators.minLength(1)]],
    preference: ['', [Validators.required, Validators.maxLength(254), Validators.minLength(1)]],
    relationship: ['alone', [Validators.required]],
    sex: ['female', [Validators.required]],
    target: ['', [Validators.required, Validators.maxLength(254), Validators.minLength(1)]],
  });

  constructor(
    private route: ActivatedRoute,
    protected careerService: CareerService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
    private formBuilder: FormBuilder,
    private translateService: TranslateService,
    private dataUtils: DataUtils,
    private eventManager: EventManager,
    private alertService: AlertService
  ) {
    this.router.events.subscribe(val => {
      this.callCareerDetail();
    });
  }

  sharingFacebook(): void {
    window.open(`https://facebook.com/sharer/sharer.php?u=` + window.location.href);
  }

  ngOnInit(): void {
    console.log('loading');
    this.submitRecruitment.valueChanges.subscribe(
      data => {
        this.contentType = data.cv ? `application/${String(data.cv.split('.')[1])}` : '';
        this.formValid = this.submitRecruitment.valid;
      },
      err => {
        console.log(err);
      }
    );
  }

  callCareerDetail(): void {
    window.scroll({ top: 0 });
    this.isLoading = true;
    this.route.paramMap.subscribe(pr => {
      this.recruitmentId = pr.get('id');
    });

    this.careerService.find(this.recruitmentId).subscribe(
      (res: HttpResponse<IRecruitmen>) => {
        this.careerDetail = { ...res.body };
        this.isLoading = true;
        this.listBenefit = this.careerDetail.benefit?.split('\n');
        this.listDescription = this.careerDetail.description?.split('\n');
        this.listRequire = this.careerDetail.require?.split('\n');
      },
      () => (this.isLoading = false)
    );

    this.careerService
      .query({
        page: 0,
        size: 4,
      })
      .subscribe(
        (res: HttpResponse<IRecruitmen[]>) => {
          this.isLoading = false;
          if (res.body) {
            this.careerMoreJob = res.body;
          }
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.submitRecruitment, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('AladinTechApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }
  // modal action
  open(content: TemplateRef<HTMLButtonElement>, ariaLabelledBy: string): void {
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

  navigateToContact(): void {
    this.router.navigateByUrl('/contacts');
  }

  navigateToJob(id?: number): void {
    this.router.navigateByUrl(`/career/${Number(id)}`);
  }

  previousState(): void {
    window.history.back();
  }

  onRecruitment(): void {
    const body = { ...this.submitRecruitment.getRawValue() };
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
        recruitment: this.careerDetail,
        cvContentType: this.contentType,
        dateRegister: d,
        position: this.careerDetail?.position,
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
}

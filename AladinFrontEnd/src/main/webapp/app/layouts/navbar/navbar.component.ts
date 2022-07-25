import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { IService } from '../../service/service.model';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ContactPageService } from '../../contact/contact-page/contact-page.service';
import * as $ from 'jquery';
import { ElasticSearchService } from 'app/common-services/elastic-search-services/elastic-search.service';
import { ResponseData } from 'app/dto-models/responseData.model';

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  openAPIEnabled?: boolean;
  version = '';
  account: Account | null = null;
  listService: IService[] | null = [];
  totalItems = 0;
  navbarSearchText = '';
  responseData!: ResponseData[];
  hasData = false;
  typingTimer: any;
  currentLanguage = 'vi';
  serviceExpand!: string;
  adminExpand!: string;
  employeeExpand!: string;
  languageExpand!: string;
  constructor(
    private loginService: LoginService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    private profileService: ProfileService,
    private router: Router,
    private contactPageService: ContactPageService,
    private elasticSearchService: ElasticSearchService,
  ) {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : 'v' + VERSION;
    }
    this.router.events.subscribe((event: any) => {
      const url = event.url;
      if (url !== undefined) {
        $('.collapse').removeClass('show');
        this.navbarSearchText = '';
        if (url.includes('/search') || url === '/') {
          $('.p-input-icon-right').css('display', 'none');
        } else {
          this.responseData = [];
          $('.result-area').css('display', 'block');
          $('.p-input-icon-right').css('display', 'block');
        }
          $(window).scroll(function () {
            if (url === '/'){
              if (($(window).scrollTop() as number) > 337) {
                $('.p-input-icon-right').css('display', 'block');
              } else {
                $('.p-input-icon-right').css('display', 'none');
              }
            } else {
              $('.p-input-icon-right').css('display', 'block');
            }
          })
        }
      }
    );
  }

  pinNavbar(): void {
    const top = $('.nav-item').offset()?.top as number;
    let left = $('.nav-item').offset()?.left as number;
    let width = 0;
    let height = $('.nav-item').height() as number;
    const offset = 5;
    $('.result-area').css('display', 'none');
    $('.line').css({
      top: `${top + height + offset}px`,
    });
    $('.nav-item').mouseenter(function (event) {
      left = event.target.getBoundingClientRect().x;
      width = event.target.getBoundingClientRect().width;
      height = event.target.getBoundingClientRect().height;
      $('.line').css({
        left: `${left + offset / 2}px`,
        width: `${width - (offset - 10)}px`,
      });
    });
    $('.nav-item').mouseleave(function () {
      $('.line').css({
        width: `${0}px`,
      });
    });

    $(window).scroll(function () {
      if (($(window).scrollTop() as number) > 1) {
        $('header').css({
          position: 'fixed',
          width: '100%',
          top: 0,
          left: 0,
          zIndex: 20,
          paddingTop: '0.3rem',
          paddingBottom: '0.3rem',
        });
        $('.line').hide();
      } else {
        $('header').css({
          position: 'static',
          paddingTop: '1rem',
          paddingBottom: '1rem',
        });
        $('.line').show();
      }
    });
  }

  ngOnInit(): void {
    this.pinNavbar();
    if (this.sessionStorageService.retrieve('locale') != null) {
      this.currentLanguage = this.sessionStorageService.retrieve('locale');
    } else {
      this.sessionStorageService.store('locale', 'vi');
    }
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.openAPIEnabled = profileInfo.openAPIEnabled;
    });
    this.accountService.getAuthenticationState().subscribe(account => (this.account = account));

    this.contactPageService.query().subscribe(
      (res: HttpResponse<IService[]>) => {
        this.totalItems = Number(res.headers.get('X-Total-Count'));
        this.onSuccess(res.body, res.headers);
      },
      () => (this.totalItems = -1)
    );
    
  }

  changeLanguage(languageKey: string): void {
    this.sessionStorageService.store('locale', languageKey);
    this.currentLanguage = languageKey;
    this.translateService.use(languageKey);
    $('.other-languages').removeClass('show');
  }

  navigateToSearch(): void {
    if (this.navbarSearchText.length > 0) {
      this.closeResult();
      this.router.navigateByUrl(`/search-all?search=${this.navbarSearchText}`);
      this.navbarSearchText = '';
    }
  }

  closeResult(): void {
    $('.result-area').css('display', 'none');
  }

  onKeyUp(): void {
    this.search();
  }

  search(): void {
    if (this.navbarSearchText.length) {
      this.elasticSearchService
        .elasticSearchAll({
          query: this.navbarSearchText,
          size: 5,
          page: 0,
        })
        .subscribe(res => {
          this.responseData = res.body as ResponseData[];
        });
    } else {
      this.responseData = [];
    }
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  login(): void {
    this.loginService.login();
  }

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  private onSuccess(list: IService[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.listService = list;
  }
}

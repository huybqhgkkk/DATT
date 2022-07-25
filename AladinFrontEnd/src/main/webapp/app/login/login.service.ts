import { Injectable } from '@angular/core';
import {Location} from "@angular/common";
import { AccountService } from 'app/core/auth/account.service';
import { AuthServerProvider} from "../core/auth/auth-session.service";
import { Login } from './login.model';
import {Logout} from "./logout.model";

@Injectable({ providedIn: 'root' })
export class LoginService {
  constructor(private accountService: AccountService,
              private authServerProvider: AuthServerProvider,
              private location: Location ) {}

  login():void {
    location.href = `${location.origin}${this.location.prepareExternalUrl('oauth2/authorization/oidc')}`;
    console.log("Login success")
  }

  logout(): void {
    this.authServerProvider.logout().subscribe((logout: Logout) => {
      let logoutUrl = logout.logoutUrl;
      const redirectUri = `${location.origin}${this.location.prepareExternalUrl('/')}`;

      // if Keycloak, uri has protocol/openid-connect/token
      if (logoutUrl.includes('/protocol')) {
        logoutUrl = logoutUrl + '?redirect_uri=' + redirectUri;
      } else {
        // Okta
        //logoutUrl = logoutUrl + '?id_token_hint=' + logout.idToken + '&post_logout_redirect_uri=' + redirectUri;
      }
      window.location.href = logoutUrl;
    });
  }
}

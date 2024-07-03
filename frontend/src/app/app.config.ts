import {APP_INITIALIZER, ApplicationConfig, provideZoneChangeDetection} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {provideHttpClient} from "@angular/common/http";
import {AuthConfig, OAuthService, provideOAuthClient} from "angular-oauth2-oidc";

export const authCodeFlowConfig: AuthConfig = {
  issuer: 'http://localhost:8085/realms/my-realm',
  tokenEndpoint: 'http://localhost:8085/realms/my-realm/protocol/openid-connect/token',
  redirectUri: window.location.origin,
  clientId: 'my-client',
  responseType: 'code',
  scope: 'openid profile'
}

function initializeOAuth(oAuthService: OAuthService): Promise<void> {
  return new Promise((resolve) => {
    oAuthService.configure(authCodeFlowConfig);
    oAuthService.setupAutomaticSilentRefresh();
    oAuthService.loadDiscoveryDocumentAndLogin().then(() => resolve());
  })
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(),
    provideOAuthClient(),
    {
      provide: APP_INITIALIZER,
      useFactory: (oAuthService: OAuthService) => {
        return () => initializeOAuth(oAuthService);
      },
      multi: true,
      deps: [ OAuthService ]
    }
  ]
};

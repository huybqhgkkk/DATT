import { Route, Routes } from '@angular/router';

import { HomeComponent } from './home.component';
import { UseRuleComponent } from './use-rule/use-rule.component';

export const HOME_ROUTE: Routes = [
  {
    path: '',
    component: HomeComponent,
    data: {
      pageTitle: 'home.title',
    },
  },
  {
    path: 'use-rule',
    component: UseRuleComponent,
    data: {
      pageTitle: 'home.rule',
    },
  },
];

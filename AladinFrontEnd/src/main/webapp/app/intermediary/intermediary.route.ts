import { Route } from '@angular/router';

import { IntermediaryComponent } from './intermediary.component';

export const INTERMEDIARY_ROUTE: Route = {
  path: 'search-all',
  component: IntermediaryComponent,
  data: {
    pageTitle: 'search.title',
  },
};

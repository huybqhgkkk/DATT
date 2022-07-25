import { Route } from '@angular/router';

import { ContactPageComponent } from './contact-page.component';

export const CONTACT_ROUTE: Route = {
  path: 'contacts',
  component: ContactPageComponent,
  data: {
    pageTitle: 'contact.title',
  },
};

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { CONTACT_ROUTE } from './contact-page.route';
import { ContactPageComponent } from './contact-page.component';
import { Ng2TelInputModule } from 'ng2-tel-input';
import { NgxScrollTopModule } from 'ngx-scrolltop';
@NgModule({
  imports: [SharedModule, NgxScrollTopModule, RouterModule.forChild([CONTACT_ROUTE]), Ng2TelInputModule],
  declarations: [ContactPageComponent],
})
export class ContactPageModule {}

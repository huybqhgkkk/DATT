import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { INTERMEDIARY_ROUTE } from './intermediary.route';
import { IntermediaryComponent } from './intermediary.component';
import { NgxScrollTopModule } from 'ngx-scrolltop';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    SharedModule,
    NgxScrollTopModule,
    InputTextModule,
    ButtonModule,
    ReactiveFormsModule,
    RouterModule.forChild([INTERMEDIARY_ROUTE]),
  ],
  declarations: [IntermediaryComponent],
})
export class IntermediaryModule {}

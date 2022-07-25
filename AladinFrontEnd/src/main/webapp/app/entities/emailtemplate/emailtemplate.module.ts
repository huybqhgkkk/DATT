import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EmailtemplateComponent } from './list/emailtemplate.component';
import { EmailtemplateDetailComponent } from './detail/emailtemplate-detail.component';
import { EmailtemplateUpdateComponent } from './update/emailtemplate-update.component';
import { EmailtemplateDeleteDialogComponent } from './delete/emailtemplate-delete-dialog.component';
import { EmailtemplateRoutingModule } from './route/emailtemplate-routing.module';

@NgModule({
  imports: [SharedModule, EmailtemplateRoutingModule],
  declarations: [EmailtemplateComponent, EmailtemplateDetailComponent, EmailtemplateUpdateComponent, EmailtemplateDeleteDialogComponent],
  entryComponents: [EmailtemplateDeleteDialogComponent],
})
export class EmailtemplateModule {}

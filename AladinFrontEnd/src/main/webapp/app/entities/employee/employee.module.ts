import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EmployeeComponent } from './list/employee.component';
import { EmployeeDetailComponent } from './detail/employee-detail.component';
import { EmployeeUpdateComponent } from './update/employee-update.component';
import { EmployeeDeleteDialogComponent } from './delete/employee-delete-dialog.component';
import { EmployeeCreateComponent } from './create/employee-create.component';
import { EmployeeRoutingModule } from './route/employee-routing.module';
import { NgxScrollTopModule } from 'ngx-scrolltop';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { ImageCropperModule } from 'ngx-image-cropper';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';

@NgModule({
  imports: [
    SharedModule,
    EmployeeRoutingModule,
    NgxScrollTopModule,
    PdfViewerModule,
    Ng2SearchPipeModule,
    ImageCropperModule,
    DialogModule,
    ButtonModule,
  ],
  declarations: [
    EmployeeComponent,
    EmployeeDetailComponent,
    EmployeeUpdateComponent,
    EmployeeCreateComponent,
    EmployeeDeleteDialogComponent,
  ],
  entryComponents: [EmployeeDeleteDialogComponent],
})
export class EmployeeModule {}

import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { KiEmployeeComponent } from './list/ki-employee.component';
import { KiEmployeeDetailComponent } from './detail/ki-employee-detail.component';
import { KiEmployeeUpdateComponent } from './update/ki-employee-update.component';
import { KiEmployeeDeleteDialogComponent } from './delete/ki-employee-delete-dialog.component';
import { kiEmployeeCreateComponent } from  './create/ki-employee-create.component'
import { KiEmployeeRoutingModule } from './route/ki-employee-routing.module';
import {NgxScrollTopModule} from "ngx-scrolltop";

@NgModule({
    imports: [SharedModule, KiEmployeeRoutingModule, NgxScrollTopModule],
  declarations: [KiEmployeeComponent, KiEmployeeDetailComponent, KiEmployeeUpdateComponent, kiEmployeeCreateComponent, KiEmployeeDeleteDialogComponent],
  entryComponents: [KiEmployeeDeleteDialogComponent],
})
export class KiEmployeeModule {}

import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RecruitmenComponent } from './list/recruitmen.component';
import { RecruitmenDetailComponent } from './detail/recruitmen-detail.component';
import { RecruitmenUpdateComponent } from './update/recruitmen-update.component';
import { RecruitmenDeleteDialogComponent } from './delete/recruitmen-delete-dialog.component';
import { RecruitmenRoutingModule } from './route/recruitmen-routing.module';

@NgModule({
  imports: [SharedModule, RecruitmenRoutingModule],
  declarations: [RecruitmenComponent, RecruitmenDetailComponent, RecruitmenUpdateComponent, RecruitmenDeleteDialogComponent],
  entryComponents: [RecruitmenDeleteDialogComponent],
})
export class RecruitmenModule {}

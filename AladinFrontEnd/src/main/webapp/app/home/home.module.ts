import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { SlideImageComponent } from './slide-image/slide-image.component';
import { UseRuleComponent } from './use-rule/use-rule.component';
import { NgxScrollTopModule } from 'ngx-scrolltop';
import { CarouselModule } from 'primeng/carousel';

@NgModule({
  imports: [SharedModule, NgxScrollTopModule, CarouselModule, RouterModule.forChild(HOME_ROUTE)],
  declarations: [HomeComponent, SlideImageComponent, UseRuleComponent],
})
export class HomeModule {}

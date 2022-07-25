import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'candidate',
        data: { pageTitle: 'AladinTechApp.candidate.home.title' },
        loadChildren: () => import('./candidate/candidate.module').then(m => m.CandidateModule),
      },

      {
        path: 'recruitment',
        data: { pageTitle: 'AladinTechApp.recruitmen.home.title' },
        loadChildren: () => import('./recruitmen/recruitmen.module').then(m => m.RecruitmenModule),
      },
      {
        path: 'services',
        data: { pageTitle: 'AladinTechApp.services.home.title' },
        loadChildren: () => import('./services/services.module').then(m => m.ServicesModule),
      },
      {
        path: 'product',
        data: { pageTitle: 'AladinTechApp.product.home.title' },
        loadChildren: () => import('./product/product.module').then(m => m.ProductModule),
      },
      {
        path: 'contact',
        data: { pageTitle: 'AladinTechApp.contact.home.title' },
        loadChildren: () => import('./contact/contact.module').then(m => m.ContactModule),
      },
      {
        path: 'candidate',
        data: { pageTitle: 'AladinTechApp.candidate.home.title' },
        loadChildren: () => import('./candidate/candidate.module').then(m => m.CandidateModule),
      },
      {
        path: 'employee',
        data: { pageTitle: 'AladinTechApp.employee.home.title' },
        loadChildren: () => import('./employee/employee.module').then(m => m.EmployeeModule),
      },
      {
        path: 'ki-employee',
        data: { pageTitle: 'AladinTechApp.kiEmployee.home.title' },
        loadChildren: () => import('./ki-employee/ki-employee.module').then(m => m.KiEmployeeModule),
      },
      {
        path: 'recruitmen',
        data: { pageTitle: 'AladinTechApp.recruitmen.home.title' },
        loadChildren: () => import('./recruitmen/recruitmen.module').then(m => m.RecruitmenModule),
      },
      {
        path: 'news',
        data: { pageTitle: 'AladinTechApp.news.home.title' },
        loadChildren: () => import('./news/news.module').then(m => m.NewsModule),
      },
      {
        path: 'department',
        data: { pageTitle: 'AladinTechApp.department.home.title' },
        loadChildren: () => import('./department/department.module').then(m => m.DepartmentModule),
      },
      {
        path: 'emailtemplate',
        data: { pageTitle: 'AladinTechApp.department.home.title' },
        loadChildren: () => import('./emailtemplate/emailtemplate.module').then(m => m.EmailtemplateModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}

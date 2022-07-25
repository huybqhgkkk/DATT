import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmailtemplate } from '../emailtemplate.model';

@Component({
  selector: 'jhi-emailtemplate-detail',
  templateUrl: './emailtemplate-detail.component.html',
})
export class EmailtemplateDetailComponent implements OnInit {
  emailtemplate: IEmailtemplate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emailtemplate }) => {
      this.emailtemplate = emailtemplate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

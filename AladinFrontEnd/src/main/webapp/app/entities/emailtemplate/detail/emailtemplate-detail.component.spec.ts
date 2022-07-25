import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmailtemplateDetailComponent } from './emailtemplate-detail.component';

describe('Emailtemplate Management Detail Component', () => {
  let comp: EmailtemplateDetailComponent;
  let fixture: ComponentFixture<EmailtemplateDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmailtemplateDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ emailtemplate: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EmailtemplateDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EmailtemplateDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load emailtemplate on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.emailtemplate).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

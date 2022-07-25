import { TestBed } from '@angular/core/testing';

import { ElasticSearchService } from './elastic-search.service';

describe('ServiceService', () => {
  let service: ElasticSearchService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ElasticSearchService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

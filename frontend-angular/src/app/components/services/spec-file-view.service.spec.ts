import { TestBed } from '@angular/core/testing';

import { SpecFileViewService } from './spec-file-view.service';

describe('SpecFileViewService', () => {
  let service: SpecFileViewService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SpecFileViewService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

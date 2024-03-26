import { TestBed } from '@angular/core/testing';

import { ParsedFileViewService } from './parsed-file-view.service';

describe('ParsedFileViewService', () => {
  let service: ParsedFileViewService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParsedFileViewService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

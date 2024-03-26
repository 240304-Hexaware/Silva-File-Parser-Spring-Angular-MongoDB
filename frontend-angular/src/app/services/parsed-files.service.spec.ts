import { TestBed } from '@angular/core/testing';

import { ParsedFilesService } from './parsed-files.service';

describe('ParsedFilesService', () => {
  let service: ParsedFilesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParsedFilesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

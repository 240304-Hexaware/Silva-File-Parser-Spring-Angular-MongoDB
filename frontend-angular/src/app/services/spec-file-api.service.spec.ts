import { TestBed } from '@angular/core/testing';

import { SpecFileApiService } from './spec-file-api.service';

describe('SpecFileApiService', () => {
  let service: SpecFileApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SpecFileApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

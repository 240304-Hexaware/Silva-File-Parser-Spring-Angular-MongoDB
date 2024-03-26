import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParsedFileViewComponent } from './parsed-file-view.component';

describe('ParsedFileViewComponent', () => {
  let component: ParsedFileViewComponent;
  let fixture: ComponentFixture<ParsedFileViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ParsedFileViewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ParsedFileViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewParsedFileViewComponent } from './new-parsed-file-view.component';

describe('NewParsedFileViewComponent', () => {
  let component: NewParsedFileViewComponent;
  let fixture: ComponentFixture<NewParsedFileViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewParsedFileViewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NewParsedFileViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

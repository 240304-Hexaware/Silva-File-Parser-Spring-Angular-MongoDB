import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddParsedFilePopupComponent } from './add-parsed-file-popup.component';

describe('AddParsedFilePopupComponent', () => {
  let component: AddParsedFilePopupComponent;
  let fixture: ComponentFixture<AddParsedFilePopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddParsedFilePopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddParsedFilePopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

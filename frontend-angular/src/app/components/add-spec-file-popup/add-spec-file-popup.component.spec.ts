import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSpecFilePopupComponent } from './add-spec-file-popup.component';

describe('AddSpecFilePopupComponent', () => {
  let component: AddSpecFilePopupComponent;
  let fixture: ComponentFixture<AddSpecFilePopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddSpecFilePopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddSpecFilePopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

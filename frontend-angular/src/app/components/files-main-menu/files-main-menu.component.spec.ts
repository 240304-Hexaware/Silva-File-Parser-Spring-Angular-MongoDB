import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilesMainMenuComponent } from './files-main-menu.component';

describe('FilesMainMenuComponent', () => {
  let component: FilesMainMenuComponent;
  let fixture: ComponentFixture<FilesMainMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FilesMainMenuComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FilesMainMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

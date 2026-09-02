import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Qcm } from './qcm';

describe('Qcm', () => {
  let component: Qcm;
  let fixture: ComponentFixture<Qcm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Qcm],
    }).compileComponents();

    fixture = TestBed.createComponent(Qcm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { CheckboxModule } from 'primeng/checkbox';

@Component({
  selector: 'app-address-form-dialog',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    DialogModule,
    ButtonModule,
    InputTextModule,
    CheckboxModule,
  ],
  templateUrl: './address-form-dialog.component.html',
  styleUrls: ['./address-form-dialog.component.scss'],
})
export class AddressFormDialogComponent {
  @Input() visible = false;
  @Input() mode: 'create' | 'edit' = 'create';
  @Input() initialValue: any | null = null;

  @Output() visibleChange = new EventEmitter<boolean>();
  @Output() submitForm = new EventEmitter<any>();

  form = this.fb.group({
    label: ['', [Validators.required, Validators.maxLength(60)]],
    line1: ['', [Validators.required, Validators.maxLength(160)]],
    line2: [''],
    city: ['', [Validators.required, Validators.maxLength(80)]],
    state: ['', [Validators.required, Validators.maxLength(80)]],
    country: [
      'PE',
      [Validators.required, Validators.minLength(2), Validators.maxLength(2)],
    ],
    postalCode: [''],
    reference: [''],
    isDefault: [false],
  });

  constructor(private fb: FormBuilder) {}

  ngOnChanges(): void {
    if (this.initialValue) {
      this.form.patchValue({
        label: this.initialValue.label,
        line1: this.initialValue.line1,
        line2: this.initialValue.line2,
        city: this.initialValue.city,
        state: this.initialValue.state,
        country: this.initialValue.country,
        postalCode: this.initialValue.postalCode,
        reference: this.initialValue.reference,
        isDefault: !!this.initialValue.isDefault,
      });
    } else {
      this.form.reset({ country: 'PE', isDefault: false });
    }
  }

  close() {
    this.visibleChange.emit(false);
  }

  save() {
    console.log(`[AddressFormDialogComponent] submit`, this.form.getRawValue());
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.submitForm.emit(this.form.getRawValue());
  }
}

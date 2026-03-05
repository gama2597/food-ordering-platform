import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';

import {
  UserApiService,
  AddressListResponse,
  UserProfile,
  Address
} from 'src/app/features/usuarios/services/user-api.service';

// PrimeNG
import { CardModule } from 'primeng/card';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { ButtonModule } from 'primeng/button';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService } from 'primeng/api';
import { ToastService } from 'src/app/core/services/toast.service';
// Modal
import { AddressFormDialogComponent } from 'src/app/features/usuarios/components/address-form-dialog/address-form-dialog.component';

@Component({
  selector: 'app-usuarios-page',
  standalone: true,
  imports: [
    CommonModule,
    CardModule,
    TableModule,
    TagModule,
    ButtonModule,
    ConfirmDialogModule,
    AddressFormDialogComponent
  ],
  providers: [ConfirmationService],
  templateUrl: './usuarios.component.html',
  styleUrl: './usuarios.component.scss',
})
export class UsuariosComponent {
  me$: Observable<UserProfile>;
  addresses$: Observable<AddressListResponse>;

  dialogVisible = false;
  dialogMode: 'create' | 'edit' = 'create';
  selectedAddress: Address | null = null;

  constructor(
    private api: UserApiService,
    private confirmationService: ConfirmationService,
    private toast: ToastService
  ) {
    this.me$ = this.api.getMe();
    this.addresses$ = this.api.getMyAddresses();
  }

  // --- Modal actions ---
  openCreate() {
    this.dialogMode = 'create';
    this.selectedAddress = null;
    this.dialogVisible = true;
  }

  openEdit(address: Address) {
    this.dialogMode = 'edit';
    this.selectedAddress = address;
    this.dialogVisible = true;
  }

  onSubmitAddress(payload: any) {
    const req$ =
      this.dialogMode === 'create'
        ? this.api.createAddress(payload)
        : this.api.updateAddress(this.selectedAddress!.id, payload);

    req$.subscribe({
      next: () => {
        this.dialogVisible = false;
        this.reloadAddresses();
        this.toast.success("Dirección guardada exitosamente")
      },
      error: (err) => {
        console.error('Error guardando dirección', err);
      }
    });
  }

  // --- Delete ---
  confirmDelete(address: Address) {
    this.confirmationService.confirm({
      message: `¿Eliminar la dirección "${address.label}"?`,
      header: 'Confirmar eliminación',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Eliminar',
      rejectLabel: 'Cancelar',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        this.api.deleteAddress(address.id).subscribe({
          next: () => {
            this.reloadAddresses(),
            this.toast.success("Dirección eliminada exitosamente")
          },
          error: (err) => console.error('Error eliminando dirección', err)
        });
      }
    });
  }

  reloadAddresses() {
    this.addresses$ = this.api.getMyAddresses();
  }
}

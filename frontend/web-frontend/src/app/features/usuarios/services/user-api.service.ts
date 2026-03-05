import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

export interface UserProfile {
  id: string;
  username: string;
  email: string;
  fullName: string;
  phone?: string;
  roles: string[];
  createdAt: string;
  updatedAt: string;
}

export interface Address {
  id: string;
  label: string;
  line1: string;
  line2?: string;
  city: string;
  state: string;
  country: string;
  postalCode?: string;
  reference?: string;
  isDefault: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface AddressListResponse {
  items: Address[];
}

@Injectable({ providedIn: 'root' })
export class UserApiService {
  private base = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getMe() {
    return this.http.get<UserProfile>(`${this.base}/users/me`);
  }

  getMyAddresses() {
    return this.http.get<AddressListResponse>(
      `${this.base}/users/me/addresses`,
    );
  }

  createAddress(payload: any) {
    return this.http.post(`${this.base}/users/me/addresses`, payload);
  }

  updateAddress(addressId: string, payload: any) {
    return this.http.put(
      `${this.base}/users/me/addresses/${addressId}`,
      payload,
    );
  }

  deleteAddress(addressId: string) {
    return this.http.delete(`${this.base}/users/me/addresses/${addressId}`);
  }
}

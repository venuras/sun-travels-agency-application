import { Injector } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environment/environment';

export class ApiService {
  private httpClient: HttpClient;
  protected apiUrl = environment.apiUrl;
  protected version = environment.version;

  constructor(protected injector: Injector) {
    this.httpClient = injector.get(HttpClient);
  }

  // Implementing HTTP methods
  protected get<T>(path: string, options: { [param: string]: unknown } = {}): Observable<T> {
    return this.httpClient.get<T>(`${this.apiUrl}/api/${this.version}/${path}/`, options);
  }

  protected post<T>(path: string, body: unknown, options: { [param: string]: unknown } = {}): Observable<T> {
    return this.httpClient.post<T>(`${this.apiUrl}/api/${this.version}/${path}/`, body, options);
  }

  protected delete<T>(path: string, body: { [param: string]: unknown } = {}): Observable<T> {
    return this.httpClient.delete<T>(`${this.apiUrl}/api/${this.version}/${path}/`, { body });
  }
}
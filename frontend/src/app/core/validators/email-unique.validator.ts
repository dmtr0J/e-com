import {AbstractControl, AsyncValidatorFn, ValidationErrors} from '@angular/forms';
import {catchError, debounceTime, first, map, Observable, of, switchMap} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

export function validateUniqueEmail(http: HttpClient): AsyncValidatorFn {
  return (control: AbstractControl): Observable<ValidationErrors | null> => {
    return control.valueChanges
      .pipe(
        debounceTime(500),
        switchMap((email: string) => {
          return http.get<boolean>(
            `${environment.apiUrl}/user/exist`,
            { params: { email }}
          ).pipe(
            map((isTaken: boolean) => {
              return isTaken ? {emailTaken: true} : null
            }),
            catchError(() => of(null))
          );
        }),
        first()
      );
  }
}

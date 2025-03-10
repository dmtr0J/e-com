import {AbstractControl, AsyncValidatorFn, ValidationErrors} from '@angular/forms';
import {delay, map, Observable, of, switchMap} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../../environments/environment';

export function validateUniqueEmail(http: HttpClient): AsyncValidatorFn {
  return (control: AbstractControl<string>): Observable<ValidationErrors | null> => {
    return of(control.value).pipe(
      delay(500),
      switchMap(email => {
        return http.get<boolean>(
          `${environment.apiUrl}/user/exist`,
          { params: { email }}
        ).pipe(
          map(isTaken => {
            return isTaken ? { emailTaken: 'Email is already taken' } : null;
          })
        );
      })

    );



    // return control.valueChanges.pipe(
    //   debounceTime(500),
    //   switchMap(email => {
    //     return http.get<boolean>(
    //       `${environment.apiUrl}/user/exist`,
    //       { params: { email }}
    //     )
    //   }),
    //   map(isTaken => {
    //     return isTaken ? { emailTaken: 'Email is already taken' } : null;
    //   })
    // )

    //
    // return http.get<boolean>(
    //   `${environment.apiUrl}/user/exist`,
    //   {
    //     params: { email: control.value }
    //   }
    // ).pipe(
    //   map(isTaken => {
    //       return isTaken ? { emailTaken: 'Email is already taken' } : null;
    //     }
    //   )
    // );
  }
}

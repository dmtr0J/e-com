import {FormControl} from '@angular/forms';

export interface RegisterForm {
  email: FormControl<string>;
  password: FormControl<string>;
  phone: FormControl<string>;
  firstName: FormControl<string>;
  middleName: FormControl<string>;
  lastName: FormControl<string>;
}

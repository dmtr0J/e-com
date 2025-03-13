import {FormControl} from '@angular/forms';

export interface RegisterForm {
  email: FormControl<string>;
  password: FormControl<string>;
  phone: FormControl<string | null>;
  firstName: FormControl<string | null>;
  middleName: FormControl<string | null>;
  lastName: FormControl<string | null>;
}

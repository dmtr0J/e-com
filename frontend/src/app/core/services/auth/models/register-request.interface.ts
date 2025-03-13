export interface RegisterRequest {
  email: string;
  password: string;
  phone: string | null;
  firstName: string | null;
  middleName: string | null;
  lastName: string | null;
}

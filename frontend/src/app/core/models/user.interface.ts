export interface User {
  id: number;
  email: string;
  role: string;
  phone: number | null;
  firstName: string | null;
  middleName: string | null;
  lastName: string | null;
  birthDate: Date | null;
  orders: [] | null;
}

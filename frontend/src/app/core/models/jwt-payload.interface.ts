export interface JwtPayload {
  iss?: string;
  sub?: string;
  iat?: number;
  exp?: number;
  userId?: number;
  role?: string;
}

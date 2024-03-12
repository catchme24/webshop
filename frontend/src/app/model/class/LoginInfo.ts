export class LoginInfo {
  userId: string;
  currentOrderId: string;
  role: string;
  token: string;
  address: string


  constructor(userId: string, currentOrderId: string, role: string, token: string, address: string) {
    this.userId = userId;
    this.currentOrderId = currentOrderId;
    this.role = role;
    this.token = token;
    this.address = address;
  }
}

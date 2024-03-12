export class Customer {
    username: string;
    password: string;
    firstName: string;
    lastName: string;
    phoneNumber: string;
    city: string;
    street: string;
    house: number;
    apartment: number;

  constructor(username: string, password: string, firstName: string, lastName: string, phoneNumber: string, city: string, street: string, house: number, apartment: number) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.city = city;
    this.street = street;
    this.house = house;
    this.apartment = apartment;
  }
}

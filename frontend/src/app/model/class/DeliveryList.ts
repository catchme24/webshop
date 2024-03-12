

export class DeliveryList {
  id: string;
  paymentMethod: string;
  getDate: string;
  dateArrived: string;


  constructor(id: string, paymentMethod: string, getDate: string, dateArrived: string) {
    this.id = id;
    this.paymentMethod = paymentMethod;
    this.getDate = getDate;
    this.dateArrived = dateArrived;
  }
}

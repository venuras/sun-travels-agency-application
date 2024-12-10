import { ContractDetail } from "./conntract-detail.type";

export interface Contract {
  contractId?: number;
  hotelName: string;
  contractValidFrom: string;
  contractValidTill: string;
  contractDetails: ContractDetail[];
  markup: number;
  createdAt?: string;
}

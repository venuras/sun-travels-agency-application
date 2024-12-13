import { RoomCountAdultCountCombination } from "./room-count-adult-count-combination.type";

export interface SearchRequest{
    checkInDate: Date,
    noOfNights: number,
    roomCountWithNoOfAdults: RoomCountAdultCountCombination[] 
}
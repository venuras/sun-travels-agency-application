import { RoomCountAdultCountCombination } from "./room-count-adult-count-combination.type"

export interface AvailableHotel{
    contractId: number,
    hotelName: string,
    availableRoomTypesForCriterion: AvailableRoomType[]
}

interface AvailableRoomType{
    criteria: RoomCountAdultCountCombination,
    available: RoomTypewithPrice[]
}

interface RoomTypewithPrice{
    roomType: string,
    markedUpPrice: number
}
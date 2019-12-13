
export class AircraftType {
  name: string;
}

export class Airport {
  iataCode: string;
  city: string;
}

export class AirportSearchResult {
  page: number;
  pagesize: number;
  elements: Airport[];
  totalElements: number;
}

export class Flight {
  id: string;
  flightNumber: string;
  airportFrom: Airport;
  airportTo: Airport;
  takeoffTime: string;
  duration: number;
  aircraftType: AircraftType;
  seatsAvailable: number;
  seatsTaken: number;
  flightStatus: string;
}


export class FlightSearchResult {
  page: number;
  pagesize: number; 
  elements: Flight[];
  totalElements: number;
}

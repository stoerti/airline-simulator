
export interface AircraftType {
  name: string;
}

export interface Airport {
  iataCode: string;
  city: string;
  location: GeoPosition;
}

export interface GeoPosition {
  latitude: number;
  longitude: number;
}

export interface AirportSearchResult {
  page: number;
  pagesize: number;
  elements: Airport[];
  totalElements: number;
}

export interface Flight {
  id: string;
  flightNumber: string;
  airportFrom: Airport;
  airportTo: Airport;
  takeoffTime: string;
  duration: number;
  seatsAvailable: number;
  seatsTaken: number;
  flightStatus: string;
  positionLatitude: number;
  positionLongitude: number;
}


export interface FlightSearchResult {
  page: number;
  pagesize: number; 
  elements: Flight[];
  totalElements: number;
}

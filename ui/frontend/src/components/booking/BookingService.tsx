import { Moment } from 'moment';

export class FlightFilter {
  airportFrom: string;
  airportTo: string;
  flightDate: Moment;
  flightReturnDate: Moment;
}

export class AircraftType {
  name: string;
  code: string;
  hasWiFi: boolean;
  hasSeatPower: boolean;
  hasEntertainment: boolean;
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
}


export class FlightSearchResult {
  elements: Flight[];
}

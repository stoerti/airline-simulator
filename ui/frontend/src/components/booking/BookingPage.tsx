import React from 'react';

import FlightList from './FlightList';
import FlightSearchForm from './FlightSearchForm';
import { FlightFilter } from './BookingService';
import { FlightSearchResult } from 'components/flighttracker/FlightTrackerService';

export default function BookingPage() {
  const [flights, setFlights] = React.useState<FlightSearchResult>({ elements: [], totalElements: 0, page: 0, pagesize: 0 });

  function onSearch(filter: FlightFilter) {
    let url = `http://localhost:9100/flights`
    url += '?airportFrom=' + encodeURIComponent(filter.airportFrom)
    url += '&airportTo=' + encodeURIComponent(filter.airportTo)
    url += '&day=' + encodeURIComponent(filter.flightDate.format('YYYY-MM-DD'))

    fetch(url)
      .then(response => response.json())
      .then(data => setFlights(data)
      );
  }

  return (
    <div>
      <FlightSearchForm onSubmit={onSearch} />
      <FlightList flights={flights}/>
    </div>
  );
}



import React from 'react';

import FlightList from './FlightList';
import FlightSearchForm from './FlightSearchForm';
import { FlightFilter } from './BookingService';
import { FlightSearchResult } from 'components/flighttracker/FlightTrackerService';
import CssBaseline from '@mui/material/CssBaseline';
import Container from '@mui/material/Container';

export default function BookingPage() {
  const [flights, setFlights] = React.useState<FlightSearchResult>({ elements: [], totalElements: 0, page: 0, pagesize: 0 });

  const onSearch = (filter: FlightFilter) => {
    let url = `http://localhost:9100/flights`
    url += '?airportFrom=' + encodeURIComponent(filter.airportFrom)
    url += '&airportTo=' + encodeURIComponent(filter.airportTo)
    url += '&day=' + encodeURIComponent(filter.flightDate.format('YYYY-MM-DD'))

    fetch(url)
      .then(response => response.json())
      .then(data => setFlights(data)
      );
  }

  const onSelectFlight = (flightId: String) => {
    fetch('http://localhost:9100/booking', {
      method: 'put',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ customerUuid: '11b69003-e332-4b2a-afcc-087970f0a63a', flightUuids: [flightId] })
    });
  }

  return (
    <React.Fragment>
      <CssBaseline />
      <Container maxWidth='md'>
        <FlightSearchForm onSubmit={onSearch} />
        <FlightList flights={flights} onSelectFlight={onSelectFlight} />
      </Container>
    </React.Fragment>
  );
}



import React from 'react';

import { makeStyles } from '@mui/styles';

import { FlightSearchResult } from './BookingService';
import FlightRow from './FlightRow';

const useStyles = makeStyles(theme => ({
  root: {
    width: '100%'
  },
  paper: {
//    marginTop: theme.spacing(3),
    width: '100%',
    overflowX: 'auto',
//    marginBottom: theme.spacing(2)
  },
  table: {
    minWidth: 650
  }
}));

interface FlightListProps {
  flights: FlightSearchResult;
  onSelectFlight: (flightId: string) => void;
}

export default function FlightList(props: FlightListProps) {

  const classes = useStyles();
  return (
      <div>
            {props.flights.elements.map(row => (
              <FlightRow flight={row} onSelectFlight={props.onSelectFlight}/>
            ))}
      </div>
  );
}

import React from 'react';

import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

import { Flight, FlightSearchResult } from './BookingService';

const useStyles = makeStyles(theme => ({
  root: {
    width: '100%'
  },
  paper: {
    marginTop: theme.spacing(3),
    width: '100%',
    overflowX: 'auto',
    marginBottom: theme.spacing(2)
  },
  table: {
    minWidth: 650
  }
}));

interface FlightListProps {
  flights: FlightSearchResult;
}

export default function FlightList(props: FlightListProps) {

  const classes = useStyles();
  return (
    <div className={classes.root}>
      <Paper className={classes.paper}>
        <Table stickyHeader className={classes.table} size='small' aria-label='a dense table'>
          <TableHead>
            <TableRow>
              <TableCell>Flightnumber</TableCell>
              <TableCell align='right'>From</TableCell>
              <TableCell align='right'>To</TableCell>
              <TableCell align='right'>Time</TableCell>
              <TableCell align='right'>Duration</TableCell>
              <TableCell align='right'>Aircraft Type</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {props.flights.elements.map(row => (
              <TableRow key={row.id}>
                <TableCell component='th' scope='row'>
                  {row.flightNumber}
                </TableCell>
                <TableCell align='right'>{row.airportFrom.city} ({row.airportFrom.iataCode})</TableCell>
                <TableCell align='right'>{row.airportTo.city} ({row.airportTo.iataCode})</TableCell>
                <TableCell align='right'>{row.takeoffTime}</TableCell>
                <TableCell align='right'>{row.duration}</TableCell>
                <TableCell align='right'>{row.aircraftType.name}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </Paper>
    </div>
  );
}

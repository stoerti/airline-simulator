import React from 'react';

import { makeStyles } from '@mui/styles';
import Paper from '@mui/material/Paper';

import { Flight } from './BookingService';
import { Grid, Typography, Button } from '@mui/material';
import moment from 'moment';

const useStyles = makeStyles(theme => ({
  paper: {
//    padding: theme.spacing(2),
    margin: 'auto',
//    marginBottom: theme.spacing(3)
  },
  button: {
    margin: 'auto'
  }
}));

interface FlightRowProps {
  flight: Flight;
  onSelectFlight: (flightId: string) => void;
}

export default function FlightRow(props: FlightRowProps) {

  const classes = useStyles();

  return (
    <Paper className={classes.paper}>
      <Grid container spacing={2}>
        <Grid item>
          <Typography gutterBottom variant='subtitle1'>
            Takeoff: {moment(props.flight.takeoffTime).format('HH:mm')}
          </Typography>
          <Typography gutterBottom variant='body2' color='textSecondary'>
            {props.flight.airportFrom.city} ({props.flight.airportFrom.iataCode})
            </Typography>
          <Typography gutterBottom variant='subtitle1'>
            Arrival: {moment(props.flight.takeoffTime).add(props.flight.duration, 'seconds').format('HH:mm')}
          </Typography>
          <Typography gutterBottom variant='body2' color='textSecondary'>
            {props.flight.airportTo.city} ({props.flight.airportTo.iataCode})
            </Typography>
        </Grid>
        <Grid item>
          <Typography gutterBottom variant='subtitle1'>
            Aircraft: {props.flight.aircraftType.name} ({props.flight.aircraftType.code})
            </Typography>
          <Typography gutterBottom variant='body2'>
            WiFi: {props.flight.aircraftType.hasWiFi}<br />
            Entertainment: {props.flight.aircraftType.hasEntertainment}<br />
            In-Seat-Power: {props.flight.aircraftType.hasSeatPower}
          </Typography>
        </Grid>
        <Grid item>
          <Button onClick={() => props.onSelectFlight(props.flight.id)} variant='contained' color='primary' className={classes.button}>Select flight</Button>
        </Grid>
      </Grid>
    </Paper>
  );
}

import React from 'react';

import {makeStyles} from '@mui/styles';
import {Button, Paper, MenuItem, Select} from '@mui/material';

import { LocalizationProvider, DatePicker} from '@mui/lab';

import {AirportSearchResult, FlightFilter} from './BookingService';

// pick a date util library
import MomentUtils from '@date-io/moment';
import {FormControl, InputLabel, FormHelperText} from '@mui/material';
import {Moment} from 'moment';

const useStyles = makeStyles(theme => ({
    root: {
        width: '100%'
    },
    paper: {
//        marginTop: theme.spacing(3),
        width: '100%',
        overflowX: 'auto',
//        marginBottom: theme.spacing(2)
    },
    formControl: {
//        margin: theme.spacing(1),
        minWidth: 120
    },
    selectEmpty: {
//        marginTop: theme.spacing(2)
    }
}));

interface FlightSearchFormProps {
    onSubmit: (filter: FlightFilter) => void;
}

export default function FlightList(props: FlightSearchFormProps) {

    const [airports, setAirports] = React.useState<AirportSearchResult>({
        elements: [],
        totalElements: 0,
        page: 0,
        pagesize: 0
    });

    const [filter_airportFrom, setFilter_airportFrom] = React.useState<string | undefined>(undefined);
    const [filter_airportTo, setFilter_airportTo] = React.useState<string | undefined>(undefined);
    const [filter_flightDate, setFilter_flightDate] = React.useState<Moment>(new MomentUtils().date());
    const [filter_flightReturnDate, setFilter_flightReturnDate] = React.useState<Moment>(new MomentUtils().date());

    React.useEffect(() => {
        fetch(`http://localhost:9100/airports?page=0&pagesize=1000`)
            .then(response => response.json())
            .then(data => setAirports(data)
            );
    }, []);

    function onSubmit() {
        props.onSubmit({
            airportFrom: filter_airportFrom,
            airportTo: filter_airportTo,
            flightDate: filter_flightDate,
            flightReturnDate: filter_flightReturnDate
        });
    }

    const classes = useStyles();
    return (
        <Paper className={classes.paper}>
            <LocalizationProvider dateAdapter={MomentUtils}>
                <FormControl required className={classes.formControl}>
                    <InputLabel shrink id='airportFrom-label'>From</InputLabel>
                    <Select
                        labelId='airportFrom-label'
                        id='airportFrom'
                        value={filter_airportFrom}
                        onChange={e => setFilter_airportFrom(e.target.value)}
                        className={classes.selectEmpty}
                    >
                        {airports.elements.map(row => (
                            <MenuItem value={row.iataCode}>{row.city} ({row.iataCode})</MenuItem>
                        ))}
                    </Select>
                    <FormHelperText>Required</FormHelperText>
                </FormControl>
                <FormControl required className={classes.formControl}>
                    <InputLabel shrink id='airportTo-label'>To</InputLabel>
                    <Select
                        labelId='airportTo-label'
                        id='airportTo'
                        value={filter_airportTo}
                        onChange={e => setFilter_airportTo(e.target.value)}
                        className={classes.selectEmpty}
                    >
                        {airports.elements.map(row => (
                            <MenuItem value={row.iataCode}>{row.city} ({row.iataCode})</MenuItem>
                        ))}
                    </Select>
                    <FormHelperText>Required</FormHelperText>
                </FormControl>

                <DatePicker
                    disableToolbar
                    variant='inline'
                    format='MM/DD/YYYY'
                    margin='normal'
                    id='flightDate'
                    label='Flight date'
                    value={filter_flightDate}
                    onChange={e => setFilter_flightDate(e)}
                    KeyboardButtonProps={{
                        'aria-label': 'change date'
                    }}
                />
                <DatePicker
                    disableToolbar
                    variant='inline'
                    format='MM/DD/YYYY'
                    margin='normal'
                    id='flightReturnDate'
                    label='Return flight date'
                    value={filter_flightReturnDate}
                    onChange={e => setFilter_flightReturnDate(e.target.value)}
                    KeyboardButtonProps={{
                        'aria-label': 'change date'
                    }}
                />
            </LocalizationProvider>
            <Button variant='contained' color='primary' onClick={onSubmit}>
                Reload
            </Button>
        </Paper>
    );
}

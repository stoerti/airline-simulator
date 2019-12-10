import React from 'react';

import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import TablePagination from '@material-ui/core/TablePagination';
import Paper from '@material-ui/core/Paper';

import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormHelperText from '@material-ui/core/FormHelperText';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';


import './App.css';


const useStyles = makeStyles(theme => ({
  root: {
    width: '100%',
  },
  paper: {
    marginTop: theme.spacing(3),
    width: '100%',
    overflowX: 'auto',
    marginBottom: theme.spacing(2),
  },
  table: {
    minWidth: 650,
  },
}));


export default function FlightList(props) {

  const [page, setPage] = React.useState(0);
  const [airports, setAirports] = React.useState({ elements: [], totalElements: 0 });
  const [aircraftTypes, setAircraftTypes] = React.useState({ elements: [], totalElements: 0 });
  const [flights, setFlights] = React.useState({ elements: [], totalElements: 0 });
  const [rowsPerPage, setRowsPerPage] = React.useState(20);

  const [filter_airportFrom, setFilter_airportFrom] = React.useState(null);
  const [filter_airportTo, setFilter_airportTo] = React.useState(null);
  const [filter_flightStatus, setFilter_flightStatus] = React.useState(null);

  function reloadFlights() {
    let url = `http://localhost:8080/flights?page=${encodeURIComponent(page)}&pagesize=${encodeURIComponent(rowsPerPage)}`
    if (filter_airportFrom)
      url += "&airportFrom=" + encodeURIComponent(filter_airportFrom)
    if (filter_airportTo)
      url += "&airportTo=" + encodeURIComponent(filter_airportTo)
    if (filter_flightStatus)
      url += "&flightStatus=" + encodeURIComponent(filter_flightStatus)

    fetch(url)
      .then(response => response.json())
      .then(data => setFlights(data)
      )
  }

  // React.useEffect(() => {
  //   setTimeout(() => {
  //     reloadFlights();
  //   }, 1000);
  // });
  
  React.useEffect(() => {
    reloadFlights();
  }, [page, rowsPerPage, filter_airportFrom, filter_airportTo, filter_flightStatus]);
  
  React.useEffect(() => {
    fetch(`http://localhost:8080/airports?page=0&pagesize=1000`)
      .then(response => response.json())
      .then(data => setAirports(data)
      )
  }, []);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = event => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  const classes = useStyles();
  return (
    <div className={classes.root}>
      <Button variant="contained" color="primary" onClick={reloadFlights}>
        Reload
     </Button>
      <Paper className={classes.paper}>
        <Table stickyHeader className={classes.table} size="small" aria-label="a dense table">
          <TableHead>
            <TableRow>
              <TableCell>Flightnumber</TableCell>
              <TableCell align="right">From</TableCell>
              <TableCell align="right">To</TableCell>
              <TableCell align="right">Time</TableCell>
              <TableCell align="right">Duration</TableCell>
              <TableCell align="right">Aircraft Type</TableCell>
              <TableCell align="right">Status</TableCell>
            </TableRow>
          </TableHead>
          <TableHead>
            <TableRow>
              <TableCell><input type="text" /></TableCell>
              <TableCell align="right">
                <Select
                  labelId="search-airport-from-status"
                  id="search-airport-from-status"
                 value={filter_airportFrom}
                 onChange={e => setFilter_airportFrom(e.target.value)}
                >
                  <MenuItem value={null}>All</MenuItem>
                {airports.elements.map( row => (
                  <MenuItem value={row.iataCode}>{row.city} ({row.iataCode})</MenuItem>
                ))}
                </Select>
              </TableCell>
              <TableCell align="right">
                <Select
                  labelId="search-airport-to-status"
                  id="search-airport-to-status"
                 value={filter_airportTo}
                 onChange={e => setFilter_airportTo(e.target.value)}
                >
                  <MenuItem value={null}>All</MenuItem>
                {airports.elements.map( row => (
                  <MenuItem value={row.iataCode}>{row.city} ({row.iataCode})</MenuItem>
                ))}
                </Select>              
              </TableCell>
              <TableCell align="right"><input type="text" /></TableCell>
              <TableCell align="right"><input type="text" /></TableCell>
              <TableCell align="right"><input type="text" /></TableCell>
              <TableCell align="right">
                <Select
                  labelId="search-flight-status"
                  id="search-flight-status"
                 value={filter_flightStatus}
                 onChange={e => setFilter_flightStatus(e.target.value)}
                >
                  <MenuItem value={null}>All</MenuItem>
                  <MenuItem value="PLANNED">Planned</MenuItem>
                  <MenuItem value="CHECKIN_OPEN">Checkin open</MenuItem>
                  <MenuItem value="CHECKIN_CLOSED">Checkin closed</MenuItem>
                  <MenuItem value="BOARDING">Boarding</MenuItem>
                  <MenuItem value="BOARDING_COMPLETED">Boarding completed</MenuItem>
                  <MenuItem value="IN_AIR">In the air</MenuItem>
                  <MenuItem value="LANDED">Landed</MenuItem>
                </Select>

              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {flights.elements.map(row => (
              <TableRow key={row.id}>
                <TableCell component="th" scope="row">
                  {row.flightNumber}
                </TableCell>
                <TableCell align="right">{row.airportFrom.city} ({row.airportFrom.iataCode})</TableCell>
                <TableCell align="right">{row.airportTo.city} ({row.airportTo.iataCode})</TableCell>
                <TableCell align="right">{row.takeoffTime}</TableCell>
                <TableCell align="right">{row.duration}</TableCell>
                <TableCell align="right">{row.aircraftType.name}</TableCell>
                <TableCell align="right">{row.flightStatus}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
        <TablePagination
          rowsPerPageOptions={[10, 15, 20]}
          component="div"
          count={flights.totalElements}
          rowsPerPage={rowsPerPage}
          page={page}
          onChangePage={handleChangePage}
          onChangeRowsPerPage={handleChangeRowsPerPage}
        />      </Paper>
    </div>
  );
}



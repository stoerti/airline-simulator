import React from 'react';

import Button from '@material-ui/core/Button';

import './App.css';
import FlightList from './FlightList';

export default function App() {

  const [flights, setFlights] = React.useState([]);

  function reloadFlights() {
    fetch(`http://localhost:8080/flights?page=0&pagesize=100`)
      .then(response => response.json())
      .then(data => setFlights(data.elements)
      )
  }


  return (
    <div className="App">
      <Button variant="contained" color="primary" onClick={reloadFlights}>
        Reload
     </Button>
      <FlightList flights={flights} />
    </div>
  );
}



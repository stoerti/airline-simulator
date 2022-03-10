import React, { useState, useEffect, useRef } from 'react';

import GoogleMapReact from 'google-map-react';
import AirportMarker from './AirportMarker';
import AircraftMarker from './AircraftMarker';
import { AirportSearchResult, FlightSearchResult } from './FlightRadarService';
import { API_KEY } from './ApiKey';
import { Button } from '@mui/material';

function useInterval(callback: any, delay: number) {
    const savedCallback = useRef();

    // Remember the latest callback.
    useEffect(() => {
        savedCallback.current = callback;
    }, [callback]);

    // Set up the interval.
    useEffect(() => {
        function tick() {
            savedCallback.current();
        }
        if (delay !== null) {
            let id = setInterval(tick, delay);
            return () => clearInterval(id);
        }
    }, [delay]);
}


export default function FlightRadarPage(props: any) {
    const [center, setCenter] = useState({ lat: 50.033333, lng: 8.570556 });
    const [zoom, setZoom] = useState(11);

    const [airports, setAirports] = React.useState<AirportSearchResult>({ elements: [], totalElements: 0, page: 0, pagesize: 0 });
    const [flights, setFlights] = React.useState<FlightSearchResult>({ elements: [], totalElements: 0, page: 0, pagesize: 0 });

    React.useEffect(() => {
        fetch(`http://localhost:9000/airports?page=0&pagesize=1000`)
            .then(response => response.json())
            .then(data => setAirports(data)
            );
    }, []);

    useInterval(() => {
        reloadFlights();
    }, 1000);

    function reloadFlights() {
        fetch('http://localhost:9000/flights/airborne?page=0&pagesize=1000')
            .then(response => response.json())
            .then(data => setFlights(data)
            );
    }


    return (
        <div style={{ height: '100vh', width: '100%' }}>
            <Button variant='contained' color='primary' onClick={reloadFlights}>
                Reload
            </Button>
            <GoogleMapReact
                bootstrapURLKeys={{ key: API_KEY }}
                defaultCenter={center}
                defaultZoom={zoom}
            >

                {airports.elements.map(airport => (
                    <AirportMarker
                        lat={airport.location.latitude}
                        lng={airport.location.longitude}
                        name={airport.city}
                        color='blue'
                    />
                ))}
                {flights.elements.map(flight => (
                    <AircraftMarker
                        lat={flight.positionLatitude}
                        lng={flight.positionLongitude}
                        name={flight.flightNumber}
                        color='red'
                    />
                ))}
            </GoogleMapReact>
        </div>
    );
}



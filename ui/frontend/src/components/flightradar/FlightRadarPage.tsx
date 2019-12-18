import React, { useState } from 'react';

import GoogleMapReact from 'google-map-react';
import AirportMarker from './AirportMarker';

export default function FlightRadarPage(props: any) {
    const [center, setCenter] = useState({lat: 11.0168, lng: 76.9558 });
    const [zoom, setZoom] = useState(11);
    return (
        <div style={{ height: '100vh', width: '100%' }}>
            <GoogleMapReact
                bootstrapURLKeys={{ key: 'AIzaSyCadQ_BABqRuirB9uSZdm6jTm7HEG3IqhA' }}
                defaultCenter={center}
                defaultZoom={zoom}
            >
                <AirportMarker
                    lat={11.0168}
                    lng={76.9558}
                    name="My Marker"
                    color="blue"
                />
            </GoogleMapReact>
        </div>
    );
}



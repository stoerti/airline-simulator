import React from 'react';
import './marker.css';

export default function AirportMarker(props: any) {
    const { color, name, id } = props;

    return (
        <div className='airport'
             style={{ backgroundColor: color, cursor: 'pointer'}}
             title={name}
        />
    );
};

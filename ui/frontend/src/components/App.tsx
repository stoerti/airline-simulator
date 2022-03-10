import React from 'react';
import {createStyles, makeStyles} from '@mui/styles';
import Drawer from '@mui/material/Drawer';
import AppBar from '@mui/material/AppBar';
import CssBaseline from '@mui/material/CssBaseline';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import Typography from '@mui/material/Typography';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import {TrendingUp, Flight} from '@mui/icons-material';
import {BrowserRouter as Router, Route, Routes, Link} from 'react-router-dom';

import FlightTrackerPage from './flighttracker/FlightTrackerPage';
import BookingPage from './booking/BookingPage';
import FlightRadarPage from './flightradar/FlightRadarPage';

const drawerWidth = 240;

function IndexPage() {
    // @ts-ignore
    return (
        <div>
            <Typography paragraph>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt
                ut labore et dolore magna aliqua. Rhoncus dolor purus non enim praesent elementum
                facilisis leo vel. Risus at ultrices mi tempus imperdiet. Semper risus in hendrerit
                gravida rutrum quisque non tellus. Convallis convallis tellus id interdum velit laoreet id
                donec ultrices. Odio morbi quis commodo odio aenean sed adipiscing. Amet nisl suscipit
                adipiscing bibendum est ultricies integer quis. Cursus euismod quis viverra nibh cras.
                Metus vulputate eu scelerisque felis imperdiet proin fermentum leo. Mauris commodo quis
                imperdiet massa tincidunt. Cras tincidunt lobortis feugiat vivamus at augue. At augue eget
                arcu dictum varius duis at consectetur lorem. Velit sed ullamcorper morbi tincidunt. Lorem
                donec massa sapien faucibus et molestie ac.
            </Typography>
            <Typography paragraph>
                Consequat mauris nunc congue nisi vitae suscipit. Fringilla est ullamcorper eget nulla
                facilisi etiam dignissim diam. Pulvinar elementum integer enim neque volutpat ac
                tincidunt. Ornare suspendisse sed nisi lacus sed viverra tellus. Purus sit amet volutpat
                consequat mauris. Elementum eu facilisis sed odio morbi. Euismod lacinia at quis risus sed
                vulputate odio. Morbi tincidunt ornare massa eget egestas purus viverra accumsan in. In
                hendrerit gravida rutrum quisque non tellus orci ac. Pellentesque nec nam aliquam sem et
                tortor. Habitant morbi tristique senectus et. Adipiscing elit duis tristique sollicitudin
                nibh sit. Ornare aenean euismod elementum nisi quis eleifend. Commodo viverra maecenas
                accumsan lacus vel facilisis. Nulla posuere sollicitudin aliquam ultrices sagittis orci a.
            </Typography>
        </div>
    );
}


const useStyles = makeStyles((theme) =>
    createStyles({
        root: {
        	display: 'flex'
        },
        appBar: {
//        	zIndex: props => props.zIndex.drawer + 1
        },
        drawer: {
        	width: drawerWidth,
        	flexShrink: 0
        },
        drawerPaper: {
        	width: drawerWidth
        },
        content: {
        	flexGrow: 1,
//        	padding: props => props.spacing(3)
        },
//        toolbar: theme.mixins.toolbar
    })
);

export default function App() {
    const classes = useStyles();

    return (
        <Router>
            <div className={classes.root}>
                <CssBaseline/>
                <AppBar position='fixed' className={classes.appBar}>
                    <Toolbar>
                        <Typography variant='h6' noWrap>
                            Airline Simulator
                        </Typography>
                    </Toolbar>
                </AppBar>
                <Drawer
                    className={classes.drawer}
                    variant='permanent'
                    classes={{
                        paper: classes.drawerPaper,
                    }}
                >
                    <div className={classes.toolbar}/>
                    <List>
                        <ListItem button component={Link} to='/flighttracker'>
                            <ListItemIcon><TrendingUp/></ListItemIcon>
                            <ListItemText primary={'Flighttracker'}/>
                        </ListItem>
                        <ListItem button component={Link} to='/flightradar'>
                            <ListItemIcon><TrendingUp/></ListItemIcon>
                            <ListItemText primary={'Flightradar'}/>
                        </ListItem>
                        <ListItem button component={Link} to='/flightbookings'>
                            <ListItemIcon><Flight/></ListItemIcon>
                            <ListItemText primary={'Flightbooking'}/>
                        </ListItem>
                    </List>
                </Drawer>
                <main className={classes.content}>
                    <div className={classes.toolbar}/>
                    <div>
                        <Routes>
                            <Route path='/' exact component={<IndexPage />}/>
                            <Route path='/flightradar' element={<FlightRadarPage />}/>
                            <Route path='/flighttracker' element={<FlightTrackerPage />}/>
                            <Route path='/flightbookings' element={<BookingPage />}/>
                        </Routes>
                    </div>
                </main>
            </div>
        </Router>
    );
}





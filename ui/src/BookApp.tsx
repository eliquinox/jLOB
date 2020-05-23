import React, {useEffect, useState} from 'react';
import BookTable from "./BookTable";
import {Grid, Typography} from "@material-ui/core";
import OrderPlacement from "./OrderPlacement";
import {Book} from "./types";
import {getBook} from "./requests";
import VwapPanel from "./VwapPanel";


function BookApp() {
    const [book, setBook] = useState<Book>();

    useEffect(() => {
        (async () => await getBook(setBook))()
    }, [])

    return (
        <div>
            <Grid container direction="column" alignItems="center" justify="center">
                <Grid item style={{ textAlign: "center", marginTop: 30, marginBottom: 10 }}>
                    <Typography variant="h4">jLOB Exchange</Typography>
                </Grid>
                <Grid
                    container
                    justify="space-between"
                    alignItems="flex-start"
                    style={{marginTop: 150, marginBottom: 10}}
                >
                    <Grid item />
                    <Grid item style={{marginLeft: 420}}>
                        <BookTable book={book}/>
                    </Grid>
                    <Grid item style={{marginRight: 50}}>
                        <Grid container direction="column">
                            <Grid item>
                                <OrderPlacement setBook={setBook}/>
                            </Grid>
                            <Grid item>
                                <VwapPanel/>
                            </Grid>
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </div>
    );
}


export default BookApp;

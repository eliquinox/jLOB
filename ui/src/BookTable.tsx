import {Book} from "./types";
import {get, keys} from "lodash";
import React from "react";
import {
    Divider,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography,
    withStyles
} from "@material-ui/core";
import {makeStyles} from "@material-ui/core/styles";


const useStyles = makeStyles({
    table: {
        minWidth: 450,
    },
});

const PriceCell = withStyles({
    body: {
        fontSize: 20,
        borderRightWidth: 1,
        borderRightStyle: "solid",
        borderRightColor: "#515151",
        borderLeftWidth: 1,
        borderLeftStyle: "solid",
        borderLeftColor: "#515151"
    }
})(TableCell);


const BidCell = withStyles({
    body: {
        fontSize: 20,
        color: "#4ea8de"
    },
})(TableCell);


const OfferCell = withStyles({
    body: {
        fontSize: 20,
        color: "#df7373",
    },
})(TableCell);


export default ({book}: {book?: Book}) => {
    const rows = bookToTableData(book)
    const classes = useStyles()
    return (
        <TableContainer component={Paper}>
            <Typography variant="h5" style={{textAlign: "center", marginTop: 10, marginBottom: 10}}>
                Limit Order Book
            </Typography>
            <Divider style={{borderColor: "#515151"}}/>
            <Table className={classes.table} size="small" aria-label="a dense table">
                <TableHead>
                    <TableRow>
                        <TableCell align="center">Offers</TableCell>
                        <TableCell align="center">Price</TableCell>
                        <TableCell align="center">Bids</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {rows.map((row) => (
                        <TableRow hover key={row.price}>
                            <OfferCell align="center">{row.offerVolume}</OfferCell>
                            <PriceCell align="center">{row.price}</PriceCell>
                            <BidCell align="center">{row.bidVolume}</BidCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>

    )
}

const bookToTableData = (book?: Book) => {
    const bids = get(book, "bids")
    const offers = get(book, "offers")

    const bidPrices = keys(bids)
    const offerPrices = keys(offers)

    return [...bidPrices, ...offerPrices].reverse().map(p => {
        const bidLevel = get(bids, p)
        const bidVolume = bidLevel ? bidLevel.placements.reduce((a, b) => a + b.size, 0) : ""

        const offerLevel = get(offers, p)
        const offerVolume = offerLevel ? offerLevel.placements.reduce((a, b) => a + b.size, 0) : ""

        return  {
            bidVolume: bidVolume,
            price: p,
            offerVolume: offerVolume
        }
    })
}

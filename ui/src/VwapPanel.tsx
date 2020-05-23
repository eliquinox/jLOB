import {makeStyles} from "@material-ui/core/styles";
import {Box, Grid, Paper, TextField, Typography} from "@material-ui/core";
import React, {useRef, useState} from "react";
import {getVwapPricing} from "./requests";

const useStyles = makeStyles({
    paper: {
        textAlign: 'center',
    },
});

const getNumberFromRef = (ref: React.MutableRefObject<any>) => Number(ref.current.valueOf().value)

const setVwapPrices = (
    size: number,
    buyPriceCallBack: (price: number) => void,
    sellPriceCallBack: (price: number) => void
) => {
    getVwapPricing('BID', size).then(r => buyPriceCallBack(r.data.price))
    getVwapPricing('OFFER', size).then(r => sellPriceCallBack(r.data.price))
}

export default () => {
    const sizeRef = useRef<any>(0)
    const [buyPrice, setBuyPrice] = useState<any>('-')
    const [sellPrice, setSellPrice] = useState<any>('-')
    const classes = useStyles()

    return (
        <Paper className={classes.paper}>
            <Box m={1} pt={1}>
                <Typography variant="h5">VWAP Pricing</Typography>
            </Box>
            <Grid container direction="column">
                <div>
                    <Box m={1} pt={1}>
                        <TextField
                            style={{maxWidth: 120}}
                            id="outlined-basic"
                            label="Size"
                            variant="outlined"
                            inputRef={sizeRef}
                            onChange={() => setVwapPrices(getNumberFromRef(sizeRef), setBuyPrice, setSellPrice)}
                        />
                    </Box>
                </div>
                <Grid container justify="space-around">
                    <Box m={1} pt={1}>
                        <Typography variant="h5">To Sell</Typography>
                        <Typography variant="h5" style={{color: "#4ea8de"}}>
                            {sellPrice}
                        </Typography>
                    </Box>
                    <Box m={1} pt={1}>
                        <Typography variant="h5">To Buy</Typography>
                        <Typography variant="h5" style={{color: "#df7373"}}>
                            {buyPrice}
                        </Typography>
                    </Box>
                </Grid>
            </Grid>
        </Paper>
    )
}
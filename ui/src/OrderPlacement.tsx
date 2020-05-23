import {
    Box,
    Button,
    FormControl,
    Grid,
    InputLabel,
    MenuItem,
    OutlinedInput,
    Paper,
    Select,
    TextField,
    Typography
} from "@material-ui/core";
import React, {useRef} from "react";
import {makeStyles} from "@material-ui/core/styles";
import {toast, ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import {Book, Order} from "./types";
import {getBook, placeOrder} from "./requests";


const useStyles = makeStyles({
    paper: {
        textAlign: 'center',
    },
});

const handleOrder = (order: Order, setBook: (book: Book) => void) => placeOrder(order)
    .then(r => toast.success((
        <div style={{ fontWeight: "bold" }}>
            Order Placed:
            <pre style={{ fontWeight: "bold" }}>
                {JSON.stringify(r.data, undefined, 2)}
            </pre>
        </div>
        ),{
                position: "top-left",
                autoClose: 5000,
                hideProgressBar: true,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
            })
    )
    .then(() => getBook(setBook))
    .catch(() => toast.error((
        <div style={{ fontWeight: "bold" }}>
            Error while placing the order!
        </div>
        ), {
            position: "top-left",
            autoClose: 5000,
            hideProgressBar: true,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined
        })
    )

export default ({setBook}: { setBook: (book: Book) => void }) => {
    const classes = useStyles()
    const sideRef = useRef<any>('')
    const priceRef = useRef<any>('')
    const sizeRef = useRef<any>('')

    return (
        <>
            <Paper className={classes.paper}>
                <Box m={1} pt={1}>
                    <Typography variant="h5">Order Entry</Typography>
                </Box>
                <form noValidate autoComplete="off" onSubmit={e => e.preventDefault()}>
                    <Grid container>
                        <Box m={1} pt={1}>
                            <FormControl variant="outlined">
                                <InputLabel>Side</InputLabel>
                                <Select
                                    style={{minWidth: 100, minHeight: 56}}
                                    id="select-outlined"
                                    inputRef={sideRef}
                                    input={<OutlinedInput labelWidth={40}/>}
                                >
                                    <MenuItem value="bid">Bid</MenuItem>
                                    <MenuItem value="offer">Offer</MenuItem>
                                </Select>
                            </FormControl>
                        </Box>
                        <Box m={1} pt={1}>
                            <TextField
                                style={{maxWidth: 120}}
                                id="outlined-basic"
                                label="Price"
                                variant="outlined"
                                inputRef={priceRef}
                            />
                        </Box>
                        <Box m={1} pt={1}>
                            <TextField
                                style={{maxWidth: 120}}
                                id="outlined-basic"
                                label="Size"
                                variant="outlined"
                                inputRef={sizeRef}
                            />
                        </Box>
                    </Grid>
                    <Box m={1} pt={1}>
                        <Button
                            type="submit"
                            variant="outlined"
                            style={{minWidth: 235, minHeight: 56, marginBottom: 10}}
                            onClick={() => handleOrder({
                                side: sideRef.current.valueOf().value,
                                price: Number(priceRef.current.valueOf().value),
                                size: Number(sizeRef.current.valueOf().value)
                            }, setBook)}
                        >
                            Place
                        </Button>
                    </Box>
                </form>
            </Paper>
            <ToastContainer
                position="top-left"
                style={{
                    minWidth: 400
                }}
                autoClose={3000}
                hideProgressBar
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
            />
        </>
    )
}
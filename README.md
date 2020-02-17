# _jLOB_
## L3 Order Book and Matching Engine Implementation in Java

jLOB has all the basic capabilities of a functional exchange. It currently supports HTTP protocol as well as basic FIX communication.

### Quickstart

To start the service:

`gradle bookHttpApp`

This will start the orderbook service on `http://localhost:4567`.

Import `jLOB.postman_collection.json` into [Postman](https://www.getpostman.com/) to start exploring the API.

_jLOB_ supports the following endpoints:

`GET /book`

Retrieves the current state of the orderbook:

*Example response*:

```
{
    "bids": {
        "90": {
            "side": "BID",
            "price": 90,
            "placements": [
                {
                    "id": 1,
                    "timestamp": 895353426935206,
                    "side": "BID",
                    "price": 90,
                    "size": 100000
                }
            ]
        }
    },
    "offers": {
        "100": {
            "side": "OFFER",
            "price": 100,
            "placements": [
                {
                    "id": 2,
                    "timestamp": 895361264216599,
                    "side": "OFFER",
                    "price": 100,
                    "size": 100000
                }
            ]
        }
    }
}
```

`POST /book`

Sends an order to the orderbook.

_Example Request:_

```
{
    "side": "offer",
    "price": 100,
    "size": 100000
}
```

_Example Response:_

```
{
    "id": 1,
    "timestamp": 895361264216599,
    "side": "OFFER",
    "price": 100,
    "size": 100000
}
```

`DEL /book`

Cancels a given amount of an existing order.

_Example Request_:

```
{
    "id": 1,
    "size": 20
}
```

_Example Response_:

```
{
    "id": 1,
    "timestamp": 896055004211197,
    "size": 20
}
```

`POST /vwap`

Retrieves a volume-weighted average price from the orderbook, given a side and a size of a hypothetical order.

_Example Request:_

```
{
    "action": "bid",
    "size": 10000
}
```

_Example Response:_

```
{
    "price": 90.0
}
```

To start FIX protocol demo, run:

`gradle bookFixApp`

The client process will exchange heartbeat messages with server process, then will send an order, followed by a market data request, to which the server will reply with an excution report.
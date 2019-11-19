# _jLOB_
## L3 Order Book and Matching Engine Implementaion in Java


### Quickstart

To start the service:

`gradle bookService`

This will start the service on `http://localhost:4567`.

Import `jLOB.postman_collection.json` into Postman to start exploring the API.

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

Retrieves a volumen-weighted average price from the orderbook, given a side and a size of a hypothetical order.

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
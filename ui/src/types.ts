export type Book = {
    bids: {[key: string]: Level}
    offers: {[key: string]: Level}
}

export type Side = "BID" | "OFFER"

export type Level = {
    side: Side
    price: number
    placements: Placement[]
}

export type Placement = {
    uuid: string
    timestamp: string
    side: Side
    price: number
    size: number
}

export type Order = {
    side?: string
    price?: number
    size?: number
}

# Nicehash Exchange CLI Client for REST API


## Single executable archive

To build a single executable jar with all dependencies run:

    mvn clean install

## Install alias for `nhx` command:

    . set-alias.sh

## Get command help

    nhx
    nhx place-order

## Specify the host to connect

By default the client will connect to API Front public endpoint at `https://api-test.nicehash.com/exchange/`.


If you want to set a different endpoint url set the env variable `NICEHASH_URL`, e.g.

    export NICEHASH_URL=http://localhost:8081



## Specify the API Key and API Secret

Before you can connect to a secured public REST API endpoint you need to set env variables `NICEHASH_API_KEY` and `NICEHASH_API_SECRET`

```
export HISTCONTROL=ignorespace
 export NICEHASH_API_KEY=YOUR_API_KEY
 export NICEHASH_API_SECRET=YOUR_API_SECRET
```

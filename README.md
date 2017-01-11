# pnc-ui-functional-tests

## prerequisities
Installed firefox

## Initialization
run the ./init.sh script before first usage

The script will download the geckodriver, create a firefox profile and instruct you how to accept the keycloak certificate for the profile.

## Running
In oreder to run the test you need to provide following system properties:
- `baseUiUrl` - the url of pnc instance (with `pnc-web/#/` suffix)
- `username` - ui user login
- `password` - ui user password

## TODOs
The biggest TODO is to add proper discriminating attributes to the html elements in the UI. 
I propose adding `data-test=<some nice name>` attributes to all elements that we're playing with.


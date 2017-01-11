#!/usr/bin/env bash

wget https://github.com/mozilla/geckodriver/releases/download/v0.12.0/geckodriver-v0.12.0-linux64.tar.gz

tar -xf geckodriver-v0.12.0-linux64.tar.gz

firefox -CreateProfile selenium-tests

echo -e "In order to finish configuring:\nRun the firefox browser with \n$ firefox -c selenium-tests\n go to the pnc-keycloak page for the pnc you are going to test and accept the certificate\n"
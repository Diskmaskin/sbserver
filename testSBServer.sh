#!/bin/bash

#Price history search
## nr=?
## price_start_date=?
## price_end_date=?
curl http://localhost:9090/sbserver/history?nr=7440201&price_start_date=1869-01-01&price_end_date=2021-01-02

#Added history
## added_start_date=?
## added_end_date=?
curl http://localhost:9090/sbserver/history?added_start_date=2018-01-01&added_end_date=2020-01-02
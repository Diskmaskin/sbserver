#!/bin/bash

echo -e "\nSBServer tester\n(This script uses firefox's clt)\n"

AND="&"

while [ 1 ]
do
    options=("Update database" "Product search" "Added history" "Price history" "Quit")
    select opt in "${options[@]}"
    do
        case $opt in
            "Update database")
                echo $opt
                ./update.sh
                ;;
            "Product search")
                echo $opt "is not implemented into this test script yet."
                ;;
            "Added history")
                echo $opt
                read -p "From date: " startdate
                read -p "To date: " enddate
                startdate="added_start_date="$startdate
                enddate="added_end_date="$enddate
                echo "URL: http://localhost:9090/sbserver/history?$startdate$AND$enddate"
                firefox http://localhost:9090/sbserver/history?$startdate$AND$enddate
                ;;
            "Price history")
                echo $opt
                read -p "Atricle number: " nr
                read -p "From date: " startdate
                read -p "To date: " enddate
                nr="nr="$nr
                startdate="price_start_date="$startdate
                enddate="price_end_date="$enddate
                echo "URL: http://localhost:9090/sbserver/history?$nr$AND$startdate$AND$enddate"
                firefox http://localhost:9090/sbserver/history?$nr$AND$startdate$AND$enddate
                ;;
            "Quit")
                echo "Exiting tester"
                exit 1
                ;;
            *) echo "invalid option $REPLY";;
        esac
    done
done

#Price history search
## nr=?
## price_start_date=?
## price_end_date=?
#curl http://localhost:9090/sbserver/history?nr=7440201&price_start_date=1869-01-01&price_end_date=2021-01-02

#Added history
## added_start_date=?
## added_end_date=?
#curl http://localhost:9090/sbserver/history?added_start_date=2018-01-01&added_end_date=2020-01-02
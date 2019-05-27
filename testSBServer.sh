#!/bin/bash

echo -e "\nSBServer tester"

options=("Update database" "Product search" "Added history" "Price history" "Quit")
select opt in "${options[@]}"
do
    case $opt in
        "Update database")
            echo $opt
            ;;
        "Product search")
            echo $opt
            ;;
        "Added history")
            echo $opt
            ;;
        "Price history")
            echo $opt
            ;;
        "Quit")
            echo "Exiting tester"
            break
            ;;
        *) echo "invalid option $REPLY";;
    esac
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
#!/bin/bash

PRIMARY_ADRESS='https://www.systembolaget.se/api/assortment/products/xml'
TEST_ADRESS='http://rameau.sandklef.com/systembolaget/20190520/products.xml'

if [[ $1 == "-t" ]]; then
    ADRESS=$TEST_ADRESS
else
    ADRESS=$PRIMARY_ADRESS
fi

echo -e "Fetching sortiment.xml...\n"
if [[ -f "resources/sortiment.xml" ]]
then
    echo -e "Removing old XML"
    rm resources/sortiment.xml
fi

if [ ! -d "resources" ]
then
    echo -e "Missing directory resources/ . Creating it."
    mkdir resources
fi
echo -e "\n\n"
wget $ADRESS -O resources/sortiment.xml
echo -e "\nDone fetching xml. Saved as resources/sortiment.xml"
sleep 1
echo -e "==========\n"
echo -e "\n=========="
echo -e "Fixing the XML indentation so it becomes human readable...\n\n"
tidy -indent -utf8 -xml resources/sortiment.xml > resources/formatted.xml
mv resources/formatted.xml resources/sortiment.xml
echo -e "\nDone fixing the xml indentation."
echo -e "==========\n\n"

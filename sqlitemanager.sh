#!/bin/bash

echo "Welcome to the database manager for TrailHeads! We'll be asking a few"
echo "short questions to add data to the database in a sane manner."

#
# Structure is as follows:  name varchar[30], coords varchar[30], difficulty smallint, description varchar[200], facilities varchar[30]
#

#
# Retrieve all necessary data, and write to database.
#
insert () {
echo -n "Name: "
read name;
echo -n "Coords: "
read coords;
echo -n "Difficulty rating: "
read difficulty;
echo -n "Description: "
read description;
echo -n "Facilities: "
read facilities;
echo -n "Length: "
read length;

sqlite3 TrailHeads.sql "insert into trails values('$name', '$coords', '$difficulty', '$description', '$facilities', '$length');";
}

#
# Initial declaration of variable to ensure function of while loop.
#
icont='y';

#
# Handles looping until done.
#
while [ $icont = 'y' ] 
do
    insert;
    echo -n "Done!  Insert another row? (y/n) "
    read cont;
    icont=`echo $cont | tr [:upper:] [:lower:]`;
done

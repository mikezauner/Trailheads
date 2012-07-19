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
# Initial call prevents asking if I'd like to add another before I've added one.
#
insert;

#
# Handles looping until done.
#
echo -n "Done!  Insert another row? (y/n) "
read cont;
icont=`echo $cont | tr [:upper:] [:lower:]`;
while [ $icont = 'y' ] 
do
    insert;
done

#!/bin/bash

echo "Welcome to the database manager for TrailHeads! We'll be asking a few"
echo "short questions to add data to the database in a sane manner."

#
# Structure is as follows:  name text, coords text, difficulty smallint, description text, facilities text, id (primary key), permit text
#

permitlogic () {
echo -n "Permit Required: "
read permit;
shopt -s nocasematch
if [[ $permit == y* ]]
then
    permitout="1"
elif [[ $permit == n* ]]
then
    permitout="0"
else
    echo "I'm sorry, I don't understand.  Restarting..."
    permitlogic;
fi

}

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
echo -n "State (HINT: use _ instead of \" \"): "
read state;

permitlogic;
sqlite3 TrailHeads.sql "insert into \"$state"_trails"\" values('$name', '$coords', '$difficulty', '$description', '$facilities', '$length', '$permitout');";
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

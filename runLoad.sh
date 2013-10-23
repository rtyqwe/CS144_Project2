#!/bin/bash

# Run the drop.sql batch file to drop existing tables
# Inside the drop.sql, you sould check whether the table exists. Drop them ONLY if they exists.
mysql CS144 < drop.sql

# Run the create.sql batch file to create the database and tables
mysql CS144 < create.sql

# Compile and run the parser to generate the appropriate load files
ant
ant run-all

# If the Java code does not handle duplicate removal, do this now
sort -u user.dat -o user.dat
sort -u item.dat -o item.dat
sort -u itemBids.dat -o itemBids.dat
sort -u bids.dat -o bids.dat
sort -u category.dat -o category.dat
sort -u itemCategory.dat -o itemCategory.dat

# Run the load.sql batch file to load the data
mysql CS144 < load.sql

# Remove .dat files to prevent appending
rm user.dat
rm item.dat
rm itemBids.dat
rm bids.dat
rm category.dat
rm itemCategory.dat
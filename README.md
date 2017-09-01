# file-parse-service

Parse and sort data in data files that include different deliminaters.

## Installation

Download from https://github.com/blischalk/file-parse-service.

## Usage

NOTE: Sorting defaults to lname

Sortable fields:

- fname
- lname
- fcolor
- gender
- dob

Run tests:

    lein spec

Run tests automatically:

    lein spec -a

Parse a single file via stdin using default sorting:

    lein run - < resources/datafile1.csv <-- pipe deliminated
    lein run - < resources/datafile2.csv <-- comma deliminated
    lein run - < resources/datafile3.csv <-- space deliminated


Parse a single file via stdin and sort by date of birth:

    lein run - -s dob < resources/datafile1.csv <-- pipe deliminated

Parse and sort ALL data files in the resources directory:

    lein run - -r


Parse and sort ALL data files in the resources directory by date of birth:

    lein run - -r -s dob

Start record api web service:

    lein run - -w

Api will start on at:

[http://localhost:9999/index.html](http://localhost:9999/index.html)

## License

Copyright © 2017 Brett Lischalk

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

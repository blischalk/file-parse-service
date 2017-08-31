# file-parse-service

Parse and sort data in data files that include different deliminaters.

## Installation

Download from https://github.com/blischalk/file-parse-service.

## Usage

Run tests:

    lein spec

Run tests automatically:

    lein spec -a

Parse and sort a single file via stdin:

    lein run - < resources/datafile1.csv <-- pipe deliminated
    lein run - < resources/datafile2.csv <-- comma deliminated
    lein run - < resources/datafile3.csv <-- space deliminated

Parse and sort ALL data files in the

    lein run - -r

## License

Copyright © 2017 Brett Lischalk

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

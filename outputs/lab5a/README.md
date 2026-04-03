# Lab 5A Deliverables

This folder contains the submission assets for Lab 5A: relational database design and implementation for Advantis Dental Surgeries (ADS).

## Included Files

- `ads-er-diagram.svg`: editable ER diagram source
- `ads-er-diagram.png`: image export of the ER diagram
- `myADSDentalSurgeryDBScript.sql`: the required SQL script containing schema, dummy data, business-rule triggers, and the four required queries
- `ads-dental-surgery.sqlite`: SQLite database generated from the SQL script
- `queries/`: helper query files for taking screenshots
- `query-results/`: text captures of each query result after execution
- `screenshots/`: place the required screenshot evidence here before final submission

## RDBMS Used

SQLite was used for the physical relational database implementation.

## Rebuild The Database

```bash
bash outputs/lab5a/build-lab5a-sqlite.sh
```

## Run A Screenshot Query

Example:

```bash
sqlite3 -header -column outputs/lab5a/ads-dental-surgery.sqlite < outputs/lab5a/queries/query-01-all-dentists.sql
```

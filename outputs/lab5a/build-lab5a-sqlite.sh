#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
DB_PATH="${SCRIPT_DIR}/ads-dental-surgery.sqlite"
SQL_SCRIPT="${SCRIPT_DIR}/myADSDentalSurgeryDBScript.sql"
QUERY_DIR="${SCRIPT_DIR}/queries"
RESULT_DIR="${SCRIPT_DIR}/query-results"

mkdir -p "${RESULT_DIR}"
rm -f "${DB_PATH}"

sqlite3 "${DB_PATH}" < "${SQL_SCRIPT}" > "${RESULT_DIR}/00_script_execution_output.txt"

sqlite3 -header -column "${DB_PATH}" < "${QUERY_DIR}/query-01-all-dentists.sql" > "${RESULT_DIR}/query-01-all-dentists.txt"
sqlite3 -header -column "${DB_PATH}" < "${QUERY_DIR}/query-02-appointments-for-dentist-D105.sql" > "${RESULT_DIR}/query-02-appointments-for-dentist-D105.txt"
sqlite3 -header -column "${DB_PATH}" < "${QUERY_DIR}/query-03-appointments-by-surgery-location.sql" > "${RESULT_DIR}/query-03-appointments-by-surgery-location.txt"
sqlite3 -header -column "${DB_PATH}" < "${QUERY_DIR}/query-04-patient-P105-on-2013-09-14.sql" > "${RESULT_DIR}/query-04-patient-P105-on-2013-09-14.txt"

echo "SQLite database rebuilt at: ${DB_PATH}"
echo "Query results saved under: ${RESULT_DIR}"

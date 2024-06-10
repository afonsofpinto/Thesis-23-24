       IDENTIFICATION DIVISION.
       PROGRAM-ID. testecob.
       
       ENVIRONMENT DIVISION.
       INPUT-OUTPUT SECTION.
       FILE-CONTROL.
           SELECT PERSON-FILE ASSIGN TO  NAME-FILE
               ORGANIZATION IS LINE SEQUENTIAL.
       
       DATA DIVISION.
       FILE SECTION.
       FD PERSON-FILE.
       01 PERSON-RECORD.
          05 XNAMEX     PIC X(30).
          05 XDATEX     PIC X(30).
          05 XCONTACTX    PIC 99.
          05 XDISTRICTX   PIC X(50).
          05 XMUNICIPALITYX  PIC X(50).
          05 XCONTRACTX  PIC 99.
       
       WORKING-STORAGE SECTION.
       01 EOF           PIC X VALUE 'N'.
       01 PERSON-INPUT.
          05 XNAMEX-IN     PIC X(30).
          05 XDATE-IN     PIC X(30).
          05 XCONTACT-IN    PIC 99.
          05 XDISTRICTX-IN   PIC X(50).
          05 XMUNICIPALITYX-IN   PIC X(50).
          05 XCONTRACTX-IN  PIC 99.
       01 NAME-FILE     PIC X(50).
       01 CURRENT-DATE-DATA.
           05  CURRENT-DATE.
               10  CURRENT-YEAR         PIC 9(04).
               10  CURRENT-MONTH        PIC 9(02).
               10  CURRENT-DAY          PIC 9(02).
           05  WS-CURRENT-TIME.
               10  CURRENT-HOURS        PIC 9(02).
               10  CURRENT-MINUTE       PIC 9(02).
               10  CURRENT-SECOND       PIC 9(02).
               10  CURRENT-MILLISECONDS PIC 9(02).
       PROCEDURE DIVISION.
           MOVE FUNCTION CURRENT-DATE to CURRENT-DATE-DATA
           MOVE "..\output\data_" TO NAME-FILE
           STRING CURRENT-YEAR CURRENT-MONTH CURRENT-DAY
               CURRENT-HOURS CURRENT-MINUTE CURRENT-SECOND
               CURRENT-MILLISECONDS ".txt"
               DELIMITED BY SIZE INTO NAME-FILE(16:35)
           OPEN OUTPUT PERSON-FILE.
           PERFORM UNTIL EOF = 'Y'
           ACCEPT PERSON-INPUT
           IF XNAMEX-IN = "exit"
               MOVE 'Y' TO EOF
           ELSE
               WRITE PERSON-RECORD FROM PERSON-INPUT
           END-IF
           END-PERFORM
           CLOSE PERSON-FILE
           DISPLAY "Data has been written"
           STOP RUN.
       
       


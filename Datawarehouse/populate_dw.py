import sys
import mysql.connector
import datetime
from config import Config

def populate_dim_time(connection):
    cursor = connection.cursor()
   
    months_data = [
        (1, 31),  # January
        (2, 29),  # February 
        (3, 31),  # March
        (4, 30),  # April
        (5, 31),  # May
        (6, 30),  # June
        (7, 31),  # July
        (8, 31),  # August
        (9, 30),  # September
        (10, 31), # October
        (11, 30), # November
        (12, 31)  # December
    ]

    for month, days in months_data:
        for day in range(1, days + 1):
            current_datetime = datetime.datetime(2024, month, day)
            sql = "INSERT INTO dim_time (DATE_) VALUES (%s)"
            val = (current_datetime,)
            cursor.execute(sql, val)

    connection.commit()
    print("dim_time table populated successfully")


def populate_dim_smart_contract(connection):
    cursor = connection.cursor()
    contracts_data = [("Descentralized_Payments", "PT BANK", "2024-01-01", "2030-01-01", "ACTIVE"),]
    for contract in contracts_data:
        sql = "INSERT INTO dim_smart_contract (CONTRACT_TYPE, CONTRACT_OWNER, CONTRACT_CREATION_DATE, CONTRACT_EXPIRE_DATE, CONTRACT_STATUS) VALUES (%s, %s, %s, %s, %s)"
        cursor.execute(sql, contract)
    connection.commit()
    print("dim_smart_contract table populated successfully")

def main():
    if len(sys.argv) != 2:
        print("ERROR: invalid number of arguments")
        sys.exit(1)
    
    input_argument = sys.argv[1]
    config = Config()
    config.setHost(input_argument)

    try:
        connection = mysql.connector.connect(
            host=config.getHost(),
            user=config.getUser(),
            password=config.getPassword(),
            database=config.getDatabase()
        )
        print("Connected to MySQL database.")
        populate_dim_time(connection)
        populate_dim_smart_contract(connection)

    except mysql.connector.Error as err:
        print("Error connecting to MySQL database:", err)

    finally:
        if 'connection' in locals() and connection.is_connected():
            connection.close()
            print("MySQL connection closed.")

if __name__ == "__main__":
    main()

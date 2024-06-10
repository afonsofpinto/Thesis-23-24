import datetime
import sys
import os
import re
import time
import mysql.connector
from config import Config

def get_matching_files():
    files = os.listdir("../output")
    pattern = re.compile(r'^data_.*$')   
    matching_files = []
    for filename in files:
        if pattern.match(filename):
            print("GOT A FILE")
            matching_files.append("../output/"+filename)
    return matching_files

def execute_db_query(connection, fields):
    try:
        data = fields[0].split()
        cursor = connection.cursor()

        #Populate the CLIENT Dimension Table
        sql = "INSERT INTO dim_client (CLIENT_NAME, CLIENT_DATE_BIRTH, CLIENT_CONTACT, DISTRICT_NAME, MUNICIPALITY_NAME) VALUES (%s, %s, %s, %s, %s)"
        val = (data[0], data[1], data[2], data[3], data[4])
        contract_id = data[5]
        cursor.execute(sql, val)
        connection.commit()
        print("Record inserted successfully in CLIENT Dimension Table")

        current_date_time = datetime.datetime.now()

        formatted_date = current_date_time.strftime('%Y-%m-%d')

        sql = "SELECT TIME_ID FROM dim_time WHERE DATE_ = %s"
        cursor.execute(sql, (formatted_date,))
        result = cursor.fetchone()
        time_id = result[0] if result else None

        #Get CLIENT_ID from the Dimension Table CLIENT
        sql = "SELECT CLIENT_ID FROM dim_client WHERE CLIENT_NAME = %s AND CLIENT_DATE_BIRTH = %s AND CLIENT_CONTACT = %s AND DISTRICT_NAME = %s AND MUNICIPALITY_NAME = %s"
        cursor.execute(sql, val)

        result = cursor.fetchone()
        CLIENT_id = result[0] if result else None

        #Populate the Fact Table
        sql = "INSERT INTO fact_bank (TIME_ID, CLIENT_SENDER_ID, CLIENT_RECEIVER_ID, CONTRACT_ID, AMOUNT,TIME_STAMP) VALUES (%s, %s, %s, %s, %s,%s)"
        cursor.execute(sql, (time_id, 3, CLIENT_id, contract_id, 1000,current_date_time))
        connection.commit()
        cursor.close()
        print("Record inserted successfully in Fact Table")
    except Exception as e:
        print("Error:", e)

def close_file(file,filename):
    file.close()
    os.remove(filename)



def add_clients(config):
    while True:
        files = get_matching_files()
        if files:
            for filename in files:
                while True:
                    try: 
                        with open(filename, 'r') as file:
                            new_line = file.readline()
                            try:
                                connection = mysql.connector.connect(host=config.getHost(), user=config.getUser(), password=config.getPassword(), database=config.getDatabase())
                            except mysql.connector.Error as e:
                                print("Error connecting to MySQL database:", e)
                                time.sleep(1)  # Wait for a while before retrying
                                continue  # Retry the connection attempt
                                
                            while new_line != '':
                                print("Pre printing Fields")
                                fields = new_line.strip().split(', ')
                                print(fields)
                                execute_db_query(connection, fields)
                                new_line = file.readline()
                            close_file(file, filename)
                        break
                    except Exception as e:
                        print("An error occurred:", e)
                        time.sleep(1)
                if 'connection' in locals() and connection.is_connected():
                    connection.close()  # Close the connection if it exists and is open
        else:
            time.sleep(30)
def main():

    if len(sys.argv) != 2:
        print("ERROR: invalid number of arguments")
        sys.exit(1)

    input_argument = sys.argv[1]
    config = Config()
    config.setHost(input_argument)
    add_clients(config)

if __name__ == "__main__":
    main()


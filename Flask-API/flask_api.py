from flask import Flask, jsonify
from flask_cors import CORS
import mysql.connector
import sys
from config import Config

app = Flask(__name__)
config = Config()

@app.route('/')
def index():
    return jsonify({'message': 'Welcome to the Flask API!'})

@app.route('/time')
def get_time():
    try:
        connection = mysql.connector.connect(
            host=config.getHost(),
            user=config.getUser(),
            password=config.getPassword(),
            database=config.getDatabase()
        )
        cursor = connection.cursor()
        cursor.execute("SELECT DATE_FORMAT(DATE_, '%Y-%m-%d'), sum(AMOUNT) as MOVIMENTED_MONEY FROM fact_bank natural join dim_time GROUP BY TIME_ID")
        data = cursor.fetchall()
        connection.close()
        return jsonify(data)
    except Exception as e:
        return jsonify({'error': str(e)})
    

@app.route('/client')
def get_client():
    try:
        connection = mysql.connector.connect(
            host=config.getHost(),
            user=config.getUser(),
            password=config.getPassword(),
            database=config.getDatabase()
        )
        cursor = connection.cursor()
        cursor.execute("""
        SELECT 
            receiver.CLIENT_ID, 
            receiver.CLIENT_NAME, 
            receiver.CLIENT_DATE_BIRTH, 
            receiver.CLIENT_CONTACT, 
            receiver.DISTRICT_NAME, 
            receiver.MUNICIPALITY_NAME, 
            SUM(fact_bank.AMOUNT) as MOVIMENTED_MONEY
        FROM 
            fact_bank
        JOIN 
            dim_client as receiver ON fact_bank.CLIENT_RECEIVER_ID = receiver.CLIENT_ID
        GROUP BY 
            receiver.CLIENT_ID, 
            receiver.CLIENT_NAME, 
            receiver.CLIENT_DATE_BIRTH, 
            receiver.CLIENT_CONTACT, 
            receiver.DISTRICT_NAME, 
            receiver.MUNICIPALITY_NAME;
        """)
        data = cursor.fetchall()
        connection.close()
        return jsonify(data)
    except Exception as e:
        return jsonify({'error': str(e)})
    
@app.route('/smartContract')
def get_contract():
    try:
        connection = mysql.connector.connect(
            host=config.getHost(),
            user=config.getUser(),
            password=config.getPassword(),
            database=config.getDatabase()
        )
        cursor = connection.cursor()
        cursor.execute("SELECT CONTRACT_ID, CONTRACT_TYPE, CONTRACT_OWNER, CONTRACT_CREATION_DATE, CONTRACT_EXPIRE_DATE, CONTRACT_STATUS, sum(AMOUNT) as MOVIMENTED_MONEY FROM fact_bank natural join dim_smart_contract GROUP BY CONTRACT_ID, CONTRACT_TYPE, CONTRACT_OWNER, CONTRACT_CREATION_DATE, CONTRACT_EXPIRE_DATE, CONTRACT_STATUS")
        data = cursor.fetchall()
        connection.close()
        return jsonify(data)
    except Exception as e:
        return jsonify({'error': str(e)})
    
@app.route('/transactions')
def get_transactions():
    try:
        connection = mysql.connector.connect(
            host=config.getHost(),
            user=config.getUser(),
            password=config.getPassword(),
            database=config.getDatabase()
        )
        cursor = connection.cursor()
        cursor.execute("""
        SELECT 
            fact_bank.TIME_STAMP as DATE,
            sender.CLIENT_ID as SENDER_ID,
            sender.CLIENT_NAME as SENDER_NAME,
            receiver.CLIENT_ID as RECEIVER_ID,
            receiver.CLIENT_NAME as RECEIVER_NAME,
            dim_smart_contract.CONTRACT_ID,
            dim_smart_contract.CONTRACT_TYPE,
            dim_smart_contract.CONTRACT_OWNER,
            fact_bank.AMOUNT as MOVIMENTED_MONEY
        FROM 
            fact_bank
        NATURAL JOIN 
            dim_smart_contract
        JOIN 
            dim_client as sender ON fact_bank.CLIENT_SENDER_ID = sender.CLIENT_ID
        JOIN 
            dim_client as receiver ON fact_bank.CLIENT_RECEIVER_ID = receiver.CLIENT_ID
        ORDER BY fact_bank.TIME_STAMP;
        """)
        data = cursor.fetchall()
        connection.close()
        return jsonify(data)
    except Exception as e:
        return jsonify({'error': str(e)})

def main():
    if len(sys.argv) != 2:
        print("ERROR: Invalid number of arguments. Please provide a configuration object.")
        sys.exit(1)
    input_argument = sys.argv[1]
    config.setHost(input_argument)
    CORS(app) 
    app.run(host='0.0.0.0', debug=True)

if __name__ == '__main__':
    main()

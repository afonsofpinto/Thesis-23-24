package pt.tecnico.blockchain.services;



import pt.tecnico.blockchain.ReplyData;
import pt.tecnico.blockchain.blockchainConnector.BlockchainConnector;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pt.tecnico.blockchain.RequestData;
import pt.tecnico.blockchain.data.Data;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClientService {
    public static final String TYPE_TRANSFER = "TYPE: EXECUTE TRANSFER";
    public static final String TYPE_CHECK_BALANCE = "TYPE: CHECK BALANCE";
    public static final String UNVALID_CLIENT = "ERROR: CLIENT DOES NOT EXIST";
    public static final String UNVALID_AMOUNT = "ERROR: UNVALID AMOUNT";
    public static final String ERROR_TRANSACTION  = "ERROR: UNABLE TO PERFORM TRANSACTION ON BLOCKCHAIN";
    public static final String ERROR_BALANCE  = "ERROR: UNABLE TO PERFORM TO CHECK BALANCE ON BLOCKCHAIN";

    public ResponseEntity<String> executeTransfer(String source, String destination, int amount) {
        try {
            if (!Data.containsKey(source)) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UNVALID_CLIENT);
            if(amount <= 0 ) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UNVALID_AMOUNT);
            System.out.println(source + "" + destination);
            ReplyData data = BlockchainConnector.executeOperation(Data.getClientPort(source),Data.getClientId(source),new RequestData(TYPE_TRANSFER,Data.getClientId(destination),amount));
            return ResponseEntity.ok(data.getReply());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_TRANSACTION);
        }
    }

    public ResponseEntity<?> getBalance(String source) {
        try {
            if (!Data.containsKey(source)) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UNVALID_CLIENT);
            ReplyData data = BlockchainConnector.executeOperation(Data.getClientPort(source),Data.getClientId(source),new RequestData(TYPE_CHECK_BALANCE));
            return ResponseEntity.ok(data.getBalance());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_BALANCE);
        }
    }
}


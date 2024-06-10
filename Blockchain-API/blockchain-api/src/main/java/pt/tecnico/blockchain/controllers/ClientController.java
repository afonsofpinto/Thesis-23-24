package pt.tecnico.blockchain.controllers;


import pt.tecnico.blockchain.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5173")
@RequestMapping(value="/operations")
public class ClientController {
    @Autowired
    ClientService service;

    @PostMapping(value="/{source}/{destination}/{amount}")
    public ResponseEntity<String> executeTransaction(@PathVariable("source") String source,@PathVariable("destination") String destination, @PathVariable("amount") int amount) throws Exception {
        return service.executeTransfer(source,destination,amount);
    }

    @GetMapping("/{source}")
    public ResponseEntity<?> getBalance(@PathVariable("source") String source) throws Exception {
        return service.getBalance(source);
    }
}




package ro.mpp2025.javaprojectui.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mpp2025.javaprojectui.CategorieVarsta;
import ro.mpp2025.javaprojectui.Proba;
import ro.mpp2025.javaprojectui.database.CategorieVarstaRepository;
import ro.mpp2025.javaprojectui.database.ProbaRepository;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/concurs/probe")
public class ProjectRestController {

    @Autowired
    private ProbaRepository probaRepository;
    @Autowired
    private CategorieVarstaRepository varstaRepository;


    @PostMapping
    public Optional<Proba> create(@RequestBody Proba proba) throws SQLException {
        System.out.println("Creating create request");

        Integer varstaId = proba.getVarsta().getId();
        CategorieVarsta varstaCompleta = varstaRepository.findOne(varstaId)
                .orElseThrow(() -> new RuntimeException("CategorieVarsta not found"));

        Proba newProba = new Proba(proba.getTip(), varstaCompleta);
        probaRepository.save(newProba);
        proba.setId(newProba.getId());
        return Optional.of(newProba);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        System.out.println("Get by id "+id);
        Optional<Proba> proba = probaRepository.findOne(id);
        if (proba == null)
            return new ResponseEntity<String>("Entity not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Optional<Proba>>(proba, HttpStatus.OK);
    }

    @GetMapping
    public Iterable<Proba> getAllProba(){
        System.out.println("Get all proba");
        return probaRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Proba proba, @PathVariable Integer id) throws SQLException {
        System.out.println("Update proba");
        Optional<Proba> probatoupdate = probaRepository.findOne(id);
        if (probatoupdate == null){
            return new ResponseEntity<String>("Entity not found", HttpStatus.NOT_FOUND);
        }

        proba.setId(id);
        Optional<Proba> updatedproba = probaRepository.update(proba);
        if (updatedproba == null)
            return new ResponseEntity<String>("Entity not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Optional<Proba>>(updatedproba, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) throws SQLException {
        System.out.println("Delete proba");
        Optional<Proba> proba = probaRepository.findOne(id);

        if(proba.isPresent()){
            probaRepository.delete(id);
            return new ResponseEntity<Integer>(HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("Nothing to delete", HttpStatus.NOT_FOUND);
        }
    }
}

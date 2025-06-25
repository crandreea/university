package ro.mpp2025.javaprojectui.client;

import com.google.protobuf.ServiceException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import ro.mpp2025.javaprojectui.CategorieVarsta;
import ro.mpp2025.javaprojectui.Proba;

import java.util.List;

public class StartProjectRestClientTest {
    public static void main(String[] args) {
        ProjectRestClient projectRestClient = new ProjectRestClient();

        CategorieVarsta varsta = new CategorieVarsta();
        varsta.setId(3);

        Proba proba = new Proba();
        proba.setTip("Desen");
        proba.setVarsta(varsta);

        try{
            //CREATE
            System.out.println("Adding a new proba...");
            Proba newProba = projectRestClient.add(proba);
            System.out.println("New proba added: " + newProba);

            //FINDALL
            System.out.println("\nPrinting all probe...");
            List<Proba> entities = projectRestClient.getAll();
            for (Proba p : entities) {
                System.out.println(p.getId() + ": " + p.getTip() + " " + p.getVarsta().getVarstaMin() + " " + p.getVarsta().getVarstaMax());
            }

            //UPDATE
            System.out.println("\nUpdating proba with id: " + newProba.getId()+ "...");
            Proba updatedProba = new Proba();
            updatedProba.setId(newProba.getId());
            updatedProba.setTip("Cautare de comori");
            updatedProba.setVarsta(varsta);

            Proba newUpdatedProba = projectRestClient.update(updatedProba, newProba.getId());
            System.out.println("New proba updated: " + newUpdatedProba);

            //FINDONE
            System.out.println("\nPrinting proba with id: " + newUpdatedProba.getId()+ "...");
            Proba fProba = projectRestClient.getById(newUpdatedProba.getId());
            System.out.println("Proba with id: " + newUpdatedProba.getId() +"---"+ fProba);

            //DELETE
            System.out.println("\nDeleting proba with id: " + newUpdatedProba.getId() + "...");
            projectRestClient.delete(newUpdatedProba.getId());
            System.out.println("Deleted proba with id" + newUpdatedProba.getId());

            //FINDALL
            System.out.println("\nPrinting all probe...");
            entities = projectRestClient.getAll();
            for (Proba p : entities) {
                System.out.println(p.getId() + ": " + p.getTip() + " " + p.getVarsta().getVarstaMin() + " " + p.getVarsta().getVarstaMax());
            }

        } catch (RestClientException | ServiceException e) {
            System.out.println("Exception: " +e.getMessage());
        }
    }
}

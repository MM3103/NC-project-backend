package my.pr.service;

import my.pr.model.Street;
import my.pr.repository.StreetRepository;
import my.pr.status.CityAndStreetStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class StreetService {
    @Autowired
    private StreetRepository repository;

    public List<Street> getAllStreets() {
        return repository.findAll();
    }

    public List<Street> getActiveStreets() {
        return repository.findStreetsByStreetStatus(CityAndStreetStatus.ACTIVE);
    }

    public List<Street> getInactiveStreets() {
        return repository.findStreetsByStreetStatus(CityAndStreetStatus.INACTIVE);
    }


    public Street getStreetById(Long id) throws EntityNotFoundException {
        return repository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Street not found for id: " + id));
    }

    public Street addStreet(Street newStreet) {
        return repository.save(newStreet);
    }

    public void deleteStreet(Long id) {
        Street street = repository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Street not found for id: " + id));
        repository.delete(street);
    }

    public void updateStreet(Long id, Street newStreet) {
        Street street = repository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Street not found for id: " + id));
        street.setName(newStreet.getName());
        street.setCity(newStreet.getCity());
        repository.save(street);
    }

    public void setStreetStatus(Long id, CityAndStreetStatus status) {
        Street street = repository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Street not found for id: " + id));
        street.setStreetStatus(status);
        repository.save(street);
    }


}

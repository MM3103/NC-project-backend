package my.pr.service;

import my.pr.model.City;
import my.pr.repository.CityRepository;
import my.pr.status.CityAndStreetStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;

    public List<City> getAllCities() {
        return repository.findAll();
    }

    public City getCityById(Long id) throws EntityNotFoundException {
        return repository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("City not found for id: " + id));
    }

    public List<City> getActiveCities() throws EntityNotFoundException {
        return repository.findCitiesByCityStatus(CityAndStreetStatus.ACTIVE);
    }

    public List<City> getInactiveCities() throws EntityNotFoundException {
        return repository.findCitiesByCityStatus(CityAndStreetStatus.INACTIVE);
    }

    public City addCity(City newCity) {
        return repository.save(newCity);
    }

    public void deleteCity(Long id) {
        City city = repository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("City not found for id: " + id));
        repository.delete(city);
    }

    public void updateCity(Long id, City newCity) {
        City city = repository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("City not found for id: " + id));
        city.setName(newCity.getName());
        repository.save(city);
    }

    public void setCityStatus(Long id, CityAndStreetStatus cityStatus) {
        City city = repository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("City not found for id: " + id));
        city.setCityStatus(cityStatus);
        repository.save(city);
    }

}

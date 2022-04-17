package my.pr.service;

import my.pr.model.City;
import my.pr.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;

    public List<City> getAllCities() {
        return repository.findAll();
    }

    public City getCity(Long id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("City not found for id: " + id));
    }

    public Long getIdByName(String name) throws EntityNotFoundException {
/*        City city = repository.findById(name).orElseThrow(() -> new EntityNotFoundException("City not found for id: " + name));*/
        City city = repository.findByName(name);
        return city.getId();
    }

    public City addCity(City newCity)  {
        return repository.save(newCity);
    }

    public void deleteCity(Long id)  {
        City city = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("City not found for id: " + id));
        repository.delete(city);
    }

    public void updateCity(Long id,City newCity){
        City city = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("City not found for id: " + id));
        city.setName(newCity.getName());
        repository.save(city);
    }

    public List<String> getCitiesNames(){
        List<City> cities = repository.findAll();
        List<String> result = new ArrayList<>();
        for (int i = 0;i < cities.size();i++){
            result.add(cities.get(i).getName());
        }
        return result;
    }
}

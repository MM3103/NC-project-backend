package my.pr.service;

import my.pr.model.Application;
import my.pr.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository repository;

    public Application add(Application newApplication) {
        return repository.save(newApplication);
    }

}

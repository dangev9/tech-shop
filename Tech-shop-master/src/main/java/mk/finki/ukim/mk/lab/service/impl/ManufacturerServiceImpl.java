package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.model.exception.ManufacturerNotFoundException;
import mk.finki.ukim.mk.lab.repository.ManufacturerRepository;
import mk.finki.ukim.mk.lab.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository repository;

    public ManufacturerServiceImpl(ManufacturerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Manufacturer> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Manufacturer findById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ManufacturerNotFoundException(id));
    }

    @Override
    public Manufacturer save(Manufacturer m) {
        return repository.save(m);
    }

    @Override
    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }
}

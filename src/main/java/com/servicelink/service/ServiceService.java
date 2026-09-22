package com.servicelink.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.servicelink.repository.ServiceRepository;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<com.servicelink.entity.Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public com.servicelink.entity.Service getServiceById(Long id) {
        return serviceRepository.findById(id).orElse(null);
    }

    public com.servicelink.entity.Service addService(com.servicelink.entity.Service service) {
        return serviceRepository.save(service);
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}
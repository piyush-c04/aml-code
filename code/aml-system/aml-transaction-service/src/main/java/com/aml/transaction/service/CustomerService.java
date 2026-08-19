package com.aml.transaction.service;

import com.aml.common.dto.request.CreateCustomerRequest;
import com.aml.common.dto.request.UpdateCustomerRequest;
import com.aml.common.dto.response.CustomerResponse;
import com.aml.common.entity.Account;
import com.aml.common.entity.Customer;
import com.aml.common.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // ------------------------------------------------------------
    // Create customer
    // ------------------------------------------------------------
    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest request) {

        Customer customer = new Customer();

        customer.setId(UUID.randomUUID().toString());
        customer.setAccountHolderType(request.getAccountHolderType());
        customer.setKycVerificationStatus(request.getKycVerificationStatus());
        customer.setRiskCountryFlag(
                request.getRiskCountryFlag() != null
                        ? request.getRiskCountryFlag()
                        : false
        );

        Customer savedCustomer = customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }

    // ------------------------------------------------------------
    // Get customer
    // ------------------------------------------------------------
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(String id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found: " + id)
                );

        return mapToResponse(customer);
    }

    // ------------------------------------------------------------
    // Get all customers
    // ------------------------------------------------------------
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ------------------------------------------------------------
    // Update customer
    // ------------------------------------------------------------
    @Transactional
    public CustomerResponse updateCustomer(
            String id,
            UpdateCustomerRequest request
    ) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found: " + id)
                );

        if (request.getAccountHolderType() != null) {
            customer.setAccountHolderType(
                    request.getAccountHolderType()
            );
        }

        if (request.getKycVerificationStatus() != null) {
            customer.setKycVerificationStatus(
                    request.getKycVerificationStatus()
            );
        }

        if (request.getRiskCountryFlag() != null) {
            customer.setRiskCountryFlag(
                    request.getRiskCountryFlag()
            );
        }

        Customer updatedCustomer = customerRepository.save(customer);

        return mapToResponse(updatedCustomer);
    }

    // ------------------------------------------------------------
    // Delete customer
    // ------------------------------------------------------------
    @Transactional
    public void deleteCustomer(String id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found: " + id)
                );

        customerRepository.delete(customer);
    }

    // ------------------------------------------------------------
    // Map entity -> response
    // ------------------------------------------------------------
    private CustomerResponse mapToResponse(Customer customer) {

    List<String> accountIds = customer.getAccounts()
            .stream()
            .map(Account::getId)
            .collect(Collectors.toList());

    CustomerResponse response = new CustomerResponse();

    response.setId(customer.getId());
    response.setAccountIds(accountIds);
    response.setAccountHolderType(customer.getAccountHolderType());
    response.setKycVerificationStatus(
            customer.getKycVerificationStatus()
    );
    response.setRiskCountryFlag(customer.getRiskCountryFlag());
    response.setCreatedAt(customer.getCreatedAt());

    return response;
}



}
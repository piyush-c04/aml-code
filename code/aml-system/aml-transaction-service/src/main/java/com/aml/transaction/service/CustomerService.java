package com.aml.transaction.service;

import com.aml.common.dto.request.CreateCustomerRequest;
import com.aml.common.dto.request.UpdateCustomerRequest;
import com.aml.common.dto.response.CustomerResponse;
import com.aml.common.entity.Account;
import com.aml.common.entity.Customer;
import com.aml.common.repository.AccountRepository;
import com.aml.common.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        if (customerRepository.findById(request.getCustomerId()).isPresent()) {
            throw new IllegalArgumentException("Customer already exists with the given customer ID");
        }

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + request.getAccountId()));

        Customer customer = Customer.builder()
                .id(request.getCustomerId())
                .account(account)
                .accountHolderType(request.getAccountHolderType())
                .kycVerificationStatus(request.getKycVerificationStatus())
                .riskCountryFlag(request.getRiskCountryFlag())
                .build();

        Customer saved = customerRepository.save(customer);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + id));
        return mapToResponse(customer);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CustomerResponse updateCustomer(String id, UpdateCustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + id));

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + request.getAccountId()));

        customer.setAccount(account);
        customer.setAccountHolderType(request.getAccountHolderType());
        customer.setKycVerificationStatus(request.getKycVerificationStatus());
        customer.setRiskCountryFlag(request.getRiskCountryFlag());

        Customer updated = customerRepository.save(customer);
        return mapToResponse(updated);
    }

    @Transactional
    public void deleteCustomer(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + id));
        customerRepository.delete(customer);
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .accountId(customer.getAccount().getId())
                .accountHash(customer.getAccount().getAccountHash())
                .accountHolderType(customer.getAccountHolderType())
                .kycVerificationStatus(customer.getKycVerificationStatus())
                .riskCountryFlag(customer.getRiskCountryFlag())
                .createdAt(customer.getCreatedAt())
                .build();
    }
}

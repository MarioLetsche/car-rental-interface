package de.superrental.carrentalinterface.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.superrental.carrentalinterface.dto.CustomerDTO;
import de.superrental.carrentalinterface.model.Customer;
import de.superrental.carrentalinterface.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerEndpointTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final String duplicateMail = "duplicateMail";

    @BeforeEach
    void setUp() {
        CustomerDTO mockCustomerDTO = new CustomerDTO("Test", "Customer", "Test.Customer@web.de", 1);
        Customer mockCustomer = new Customer(mockCustomerDTO);

        when(customerRepository.findAll()).thenReturn(List.of(mockCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(mockCustomer);
        when(customerRepository.save(argThat(customer -> customer.getEmail().equals(duplicateMail)))).thenThrow(RuntimeException.class);
        doNothing().when(customerRepository).deleteById(anyInt());
    }

    @Test
    public void getCustomersAndReturnsRepsonseEntity() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value(1));
    }

    @Test
    public void getCustomersWithContent() throws Exception {
        mockMvc.perform(get("/customers").contentType(MediaType.ALL).content("Test"))
                .andExpect(status().isOk());
    }

    @Test
    public void postCustomerWithValidCustomer() throws Exception {
        Customer mockCustomer = new Customer();
        mockCustomer.setFirstName("Test");
        mockCustomer.setLastName("Person");
        mockCustomer.setEmail("test@test.com");

        mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(mockCustomer)))
                .andExpect(status().isCreated());
    }

    @Test
    public void postCustomerWithDuplicateMail() throws Exception {
        Customer mockCustomer = new Customer();
        mockCustomer.setFirstName("Test");
        mockCustomer.setLastName("Person");
        mockCustomer.setEmail(duplicateMail);

        mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(mockCustomer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postCustomerWithCustomerId() throws Exception {
        Customer mockCustomer = new Customer();
        mockCustomer.setFirstName("Test");
        mockCustomer.setLastName("Person");
        mockCustomer.setEmail("test2@test.com");
        mockCustomer.setCustomerId(1);

        mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(mockCustomer)))
                .andExpect(status().isConflict());
    }

    @Test
    public void postCustomerWithInvalidCustomer() throws Exception {
        String invalidCustomer = "invalidCustomer";

        mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(invalidCustomer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putCustomerWithValidCustomer() throws Exception {
        Customer mockCustomer = new Customer();
        mockCustomer.setFirstName("Benjamin");
        mockCustomer.setLastName("Person");
        mockCustomer.setEmail("test@test.com");
        mockCustomer.setCustomerId(1);

        mockMvc.perform(put("/customers").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(mockCustomer)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void putCustomerWithoutCustomerId() throws Exception {
        Customer mockCustomer = new Customer();
        mockCustomer.setFirstName("Benjamin");
        mockCustomer.setLastName("Person");
        mockCustomer.setEmail("test2@test.com");

        mockMvc.perform(put("/customers").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(mockCustomer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteCustomerWithValidCustomerId() throws Exception {
        Integer customerId = 1;

        mockMvc.perform(delete("/customers").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(customerId)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteCustomerWithNonExistingId() throws Exception {
        Integer customerId = 2;

        mockMvc.perform(delete("/customers").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(customerId)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteCustomerWithInvalidCustomerIdType() throws Exception {
        String customerId = "NaN";

        mockMvc.perform(delete("/customers").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(customerId)))
                .andExpect(status().isBadRequest());
    }
}
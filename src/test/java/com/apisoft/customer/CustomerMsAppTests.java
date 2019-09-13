package com.apisoft.customer;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.apisoft.customer.dao.CustomerDao;
import com.apisoft.customer.dao.entity.Customer;
import com.apisoft.customer.exception.Errors;
import com.apisoft.customer.service.CustomerMsService;
import com.apisoft.customer.util.JsonUtils;
import com.apisoft.customer.web.CustomerMsRestController;
import com.apisoft.customer.web.api.ResponseStatus;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Salah Abu Msameh
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CustomerMsRestController.class)
public class CustomerMsAppTests {
	
	@Autowired
	private MockMvc mvc;
	
	@SpyBean
	private CustomerMsService srv;
	
	@MockBean
	private CustomerDao dao;
	
	ObjectMapper objMapper = new ObjectMapper()
			.registerModule(new JavaTimeModule());
	private List<Customer> customers;
	
	@Before
	public void init() throws Exception {
		
		customers = objMapper.readValue(
				getFileContent("default-customers-list.json"), new TypeReference<List<Customer>>(){});
		
		//init with default list of customers
		given(dao.findAll()).willReturn(customers);
	}
	
	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetAllCustomers_SuccessResponse() throws Exception {
		
		mvc.perform(get("/customers")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.response_status", is(ResponseStatus.SUCCESS.getStatus())))
				.andExpect(jsonPath("$.response_body", is(notNullValue())))
				.andExpect(jsonPath("$.response_body.customers.length()", is(1)));
	}
	
	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetCustomer_SuccessResponse() throws Exception {
		
		Customer customerOne = customers.get(0);
		given(dao.findById(Long.valueOf("1"))).willReturn(Optional.of(customerOne));
		
		MvcResult mvcResult = mvc.perform(get("/customers/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.response_status", is(ResponseStatus.SUCCESS.getStatus())))
				.andExpect(jsonPath("$.response_body", is(notNullValue())))
				.andExpect(jsonPath("$.response_body.customer").exists())
				.andReturn();
		
		String customerResponse = JsonUtils.extractJson(mvcResult.getResponse().getContentAsString(),
				"$.response_body.customer");
		String customer = objMapper.writeValueAsString(customerOne);
		
		JSONAssert.assertEquals(customer, customerResponse, JSONCompareMode.STRICT);
	}
	
	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetCustomer_BadRequestResponse() throws Exception {
		
		given(dao.findById(Long.valueOf("2"))).willReturn(Optional.empty());
		
		mvc.perform(get("/customers/2")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.response_status", is(ResponseStatus.FAILED.getStatus())))
				.andExpect(jsonPath("$.response_body").doesNotExist());
	}
	
	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetCustomer_InternalServerError() throws Exception {
		
		given(dao.findById(Long.valueOf("55"))).willThrow(new RuntimeException("Test connection fail"));
		
		mvc.perform(get("/customers/55")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.response_status", is(ResponseStatus.FAILED.getStatus())))
				.andExpect(jsonPath("$.error_code", is(Errors.INTERNAL_SERVER_ERROR.getErrorCode())))
				.andExpect(jsonPath("$.response_body").doesNotExist());
	}
	
	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testAddCustomer_SuccessResponse() throws Exception {
		
		Customer customerTwo = objMapper.readValue(getFileContent("customer-two.json"), new TypeReference<Customer>(){});
		given(dao.save(customerTwo)).willReturn(customerTwo);
		
		MvcResult mvcResult = mvc.perform(post("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsBytes(customerTwo)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.response_status", is(ResponseStatus.SUCCESS.getStatus())))
				.andExpect(jsonPath("$.response_body", is(notNullValue())))
				.andExpect(jsonPath("$.response_body.customer").exists())
				.andReturn();
		
		String customerResponse = JsonUtils.extractJson(mvcResult.getResponse().getContentAsString(),
				"$.response_body.customer");
		
		//customerTwo.setCreatedDate(LocalDate.of(2019, 8, 28));
		String customerStr = objMapper.writeValueAsString(customerTwo);
		
		JSONAssert.assertEquals(customerStr, customerResponse, JSONCompareMode.STRICT);
	}
	
	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testAddCustomer_BadRequestResponse() throws Exception {
		
		mvc.perform(post("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsBytes(new Customer())))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.response_status", is(ResponseStatus.FAILED.getStatus())))
				.andExpect(jsonPath("$.response_body").doesNotExist())
				.andExpect(jsonPath("$.request_validation_errors").exists())
				.andExpect(jsonPath("$.error_code", is(Errors.REQUEST_FIELDS_VALIDATION_ERRORS.getErrorCode())));
	}
	
	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testAddCustomer_InternalServerError() throws Exception {
		
		Customer customer = objMapper.readValue(
				getFileContent("customer-two.json"), new TypeReference<Customer>(){});
		customer.setCustomerId(Long.valueOf("222"));
		
		given(dao.save(customer))
				.willThrow(new RuntimeException("Error while saving entity to the database"));
		
		mvc.perform(post("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsBytes(customer)))
				.andDo(print())
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.response_status", is(ResponseStatus.FAILED.getStatus())))
				.andExpect(jsonPath("$.response_body").doesNotExist())
				.andExpect(jsonPath("$.error_code", is(Errors.INTERNAL_SERVER_ERROR.getErrorCode())));
	}
    
    /**
     *
     * @throws Exception
     */
    @Test
    public void testUpdateCustomer_SuccessResponse() throws Exception {
        
        Customer customerThree = objMapper.readValue(getFileContent("customer-three.json"), new TypeReference<Customer>(){});
        given(dao.findById(customerThree.getCustomerId()))
				.willReturn(Optional.of(customerThree));
        
        MvcResult mvcResult = mvc.perform(put("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objMapper.writeValueAsBytes(customerThree)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response_status", is(ResponseStatus.SUCCESS.getStatus())))
                .andExpect(jsonPath("$.response_body", is(notNullValue())))
                .andExpect(jsonPath("$.response_body.customer").exists())
                .andReturn();
        
        String customerResponse = JsonUtils.extractJson(mvcResult.getResponse().getContentAsString(), "$.response_body.customer");
        String customerStr = objMapper.writeValueAsString(customerThree);
        
        JSONAssert.assertEquals(customerStr, customerResponse, JSONCompareMode.STRICT);
    }
    
    /**
     *
     * @throws Exception
     */
    @Test
    public void testUpdateCustomer_BadRequestResponse() throws Exception {
        
        mvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objMapper.writeValueAsBytes(new Customer())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.response_status", is(ResponseStatus.FAILED.getStatus())))
                .andExpect(jsonPath("$.response_body").doesNotExist())
                .andExpect(jsonPath("$.request_validation_errors").exists())
                .andExpect(jsonPath("$.error_code", is(Errors.REQUEST_FIELDS_VALIDATION_ERRORS.getErrorCode())));
    }
    
    /**
     *
     * @throws Exception
     */
    @Test
    public void testUpdateCustomer_InternalServerError() throws Exception {
        
        Customer customer = objMapper.readValue(
                getFileContent("customer-two.json"), new TypeReference<Customer>(){});
        
        given(dao.save(customer))
                .willThrow(new RuntimeException("Error while saving entity to the database"));
        
        mvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objMapper.writeValueAsBytes(customer)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.response_status", is(ResponseStatus.FAILED.getStatus())))
                .andExpect(jsonPath("$.response_body").doesNotExist())
                .andExpect(jsonPath("$.error_code", is(Errors.INTERNAL_SERVER_ERROR.getErrorCode())));
    }
	
	/**
	 * Read file content.
	 *
	 * @param fileName file name
	 * @return
	 * @throws Exception
	 */
	private String getFileContent(String fileName) throws Exception {
		
		return new String(Files.readAllBytes(Paths.get(getClass()
				.getResource("/" + fileName)
				.toURI())));
	}
}

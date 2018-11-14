package com.N26.BestBuy.Products;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import static com.jayway.restassured.RestAssured.given;

public class Products 
{
	 @BeforeMethod
	 public void setup()
	 {
		 RestAssured.baseURI="http://localhost:3030";
	 }

	 private ProductInputDTO product1()
	 {
		 return new ProductDTOBuilder().setName("Toyota Vios").setDescription("Car").setImage("https://newcar.carlist.my/uploads/model_year_colour_images/127_large.jpg").setManufacturer("Toyota")
				 						.setModel("vios").setPrice(30).setShipping(0).setType("saloon").setUpc("Test").setUrl("https://en.wikipedia.org/wiki/Toyota_Vios").build();
	 }
	
	 /***************************** Test cases for Create Products ****************************/
	 @Test // Verify the status code after create a product
	 public void verifyStatusCodeAfterCreateProduct()
	 {
			
		 Response response = given().contentType("application/json").accept(ContentType.JSON)
							 		.body(product1())
							 		.when().post("/products"); 
		 Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED); // Assert the status code
	 }
	 
	@Test	 // Verify that service does not allows to create a product without any value
	public void verifyServiceNotAllowsToCreateProductWithoutValues()
	{
		 Response response = given().contentType("application/json").accept(ContentType.JSON)
			 		.body("")
			 		.when().post("/products"); 
		 Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST); // Assert the status code
	}
	
	@Test	// Verify the created product details
	public void verifyCreatedProductDetails ()
	{
		Response response = given().contentType("application/json").accept(ContentType.JSON)
		 		.body(product1())
		 		.when().post("/products"); 
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED); // Assert the status code
		Assert.assertEquals(response.jsonPath().getString("name"), "Toyota Vios");
		Assert.assertEquals(response.jsonPath().getString("type"), "saloon");
		Assert.assertEquals(response.jsonPath().getDouble("price"), 30.0);
		Assert.assertEquals(response.jsonPath().getString("description"), "Car");
		Assert.assertEquals(response.jsonPath().getString("model"), "vios");
	}
	
	@Test	// Verify that service allows to create product with same set of data
	public void verifyServiceAllowsToCreateProductWithSameDataSet()
	{
		Response response = given().contentType("application/json").accept(ContentType.JSON)
						 		.body(product1())
						 		.when().post("/products"); 
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED); // Assert the status code
		int product1 = response.jsonPath().getInt("id");	// Get the product ID
		
		// Create a product using same Product Object
		Response response2 = given().contentType("application/json").accept(ContentType.JSON)
		 		.body(product1())
		 		.when().post("/products"); 
		Assert.assertEquals(response2.getStatusCode(), HttpStatus.SC_CREATED); // Assert the status code
		int product2 = response2.jsonPath().getInt("id");	// Get the product ID
		Assert.assertNotEquals(product1, product2); // Assert that Two product Ids are there
	}
	
	@Test 	// VerifyThatServiceDoesNotAllowToCreateProductWithoutName()
	public void verifyServiceNotAllowsToCreateWithoutName()
	{
		ProductInputDTO proWithOutName = product1();
		proWithOutName.setName("");
		 Response response = given().contentType("application/json").accept(ContentType.JSON)
			 		.body(proWithOutName)
			 		.when().post("/products"); 
		 Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST); // Assert the status code
		 Assert.assertEquals(response.jsonPath().getString("errors[0]"), "'name' should NOT be shorter than 1 characters"); // Assert the message
	}
	
	@Test	// Verify that the system not allows to create product with name as null values
	public void verifyProductCannotCreateWithNameAsnull()
	{
		ProductInputDTO proNullName =product1();
		proNullName.setName(null);
		Response response = given().contentType("application/json").accept(ContentType.JSON)
			 		.body(proNullName)
			 		.when().post("/products"); 
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST); // Assert the status code
		Assert.assertEquals(response.jsonPath().getString("errors[0]"), "'name' should be string"); // Assert the message
	}
	
	@Test 	// VerifyThatServiceDoesNotAllowToCreateProductWithoutType()
	public void verifyServiceNotAllowsToCreateWithoutType()
	{
		ProductInputDTO prodWithoutType = product1();
		prodWithoutType.setType("");
		Response response = given().contentType("application/json").accept(ContentType.JSON)
			 		.body(prodWithoutType)
			 		.when().post("/products"); 
		 Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST); // Assert the status code
		 Assert.assertEquals(response.jsonPath().getString("errors[0]"), "'type' should NOT be shorter than 1 characters"); // Assert the message
	}
	

	/************************ Test cases for Delete Products **********************************/
	@Test	// Verify the validation message when try to delete a not existing product id
	public void verifyValidationWhenTryToDeleteNotExistingProductId()
	{
		String invalidProductID = "Test";
		Response response = given().contentType("application/json").accept(ContentType.JSON)
		 		.body("")
		 		.when().delete("/products/"+invalidProductID); 
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_NOT_FOUND);
		Assert.assertEquals(response.jsonPath().getString("message"), "No record found for id 'Test'");
	}
	
	@Test	// Verify that the service allow to delete a product
	public void verifyThatServiceAllowsToDeleteAProduct ()
	{
		// Create a product 
		Response response = given().contentType("application/json").accept(ContentType.JSON)
		 		.body(product1())
		 		.when().post("/products");
		double productID = response.jsonPath().getDouble("id");	// get the product ID
		
		// Delete the product
		Response responseD = given().contentType("application/json").accept(ContentType.JSON)
		 		.body("")
		 		.when().delete("/products/"+productID);
		Assert.assertEquals(responseD.getStatusCode(), HttpStatus.SC_OK);// Assert that the id has been deleted 
		
		// Try to get the deleted Product information
		Response responseG = given().contentType("application/json").accept(ContentType.JSON)
							 		.body("")
							 		.when().get("/products/"+productID);
		Assert.assertEquals(responseG.getStatusCode(), HttpStatus.SC_NOT_FOUND);
	}
	
	@Test // Verify that service validate when going to delete already deleted product
	public void verifyTheValidationWhenDeleteAlreadyDeletedproduct ()
	{
		// Create a product 
				Response response = given().contentType("application/json").accept(ContentType.JSON)
				 		.body(product1())
				 		.when().post("/products");
				double productID = response.jsonPath().getDouble("id");	// get the product ID
				
				// Delete the product
				Response responseD = given().contentType("application/json").accept(ContentType.JSON)
				 		.body("")
				 		.when().delete("/products/"+productID);
				Assert.assertEquals(responseD.getStatusCode(), HttpStatus.SC_OK);// Assert that the id has been deleted
				
				// Delete the same product again
				Response responseD2 = given().contentType("application/json").accept(ContentType.JSON)
				 		.body("")
				 		.when().delete("/products/"+productID);
				Assert.assertEquals(responseD2.getStatusCode(), HttpStatus.SC_NOT_FOUND);// Assert that the id has been deleted
				Assert.assertEquals(responseD2.jsonPath().getString("message"), "No record found for id '"+productID+"'"); // Assert the message
	}
	
	/***************************** Get product ****************************************************/
	@Test	//Verify that service validate wrong product Id
	public void verifyThatServiceValidateTheWrongProductID()
	{
	Response response = given().contentType("application/json").accept(ContentType.JSON)
							 		.body("")
							 		.when().get("/products/"+"Test");
	Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_NOT_FOUND);
	}
	
	@Test	//Verify that Product details are display correctly when get it 
	public void verifyProductDetailsDisplayCorrectly ()
	{
		Response response = given().contentType("application/json").accept(ContentType.JSON)
		 		.body(product1())
		 		.when().post("/products"); 
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED); // Assert the status code
		int productID = response.jsonPath().getInt("id");	// Get the product ID
		String productName = response.jsonPath().getString("name");	// Get the product Name
		double proPrice =response.jsonPath().getDouble("price"); //Get the product Price
		String proDescription 	= response.jsonPath().getString("description"); // Get the Description
		
		// Get the Product details by giving product ID
		Response responseG = given().contentType("application/json").accept(ContentType.JSON)
							 		.body("")
							 		.when().get("/products/"+productID);
		Assert.assertEquals(responseG.getStatusCode(), HttpStatus.SC_OK); // Assert the status code
		
		Assert.assertEquals(responseG.jsonPath().getInt("id"), productID);	// Assert the product ID
		Assert.assertEquals(responseG.jsonPath().getString("name"), productName);	// Assert the Product Name
		Assert.assertEquals(responseG.jsonPath().getDouble("price"), proPrice);	// Assert the Product price
		Assert.assertEquals(responseG.jsonPath().getString("description"), proDescription); // Assert the Product Description
	}
	
	
	/************************************* Update Products *********************************************/
	@Test	// Verify that the service not allows to update with wrong product ID
	public void checkValidationForInvalidProductIDWhenEdit()
	{
		Response responseU = given().contentType("application/json").accept(ContentType.JSON)
		 		.body(product1())
		 		.when().put("/products/"+"Test"); 
		Assert.assertEquals(responseU.getStatusCode(), HttpStatus.SC_NOT_FOUND); // Assert the status code
	}
	
	@Test	// Verify the product details after edit the product
	public void verifyProductDetailsAfterEdit ()
	{
		Response response = given().contentType("application/json").accept(ContentType.JSON)
		 		.body(product1())
		 		.when().post("/products"); 
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED); // Assert the status code
		int productID = response.jsonPath().getInt("id");	// Get the product ID
		Assert.assertEquals(response.jsonPath().getString("name"), "Toyota Vios"); 	// Assert the product name
		Assert.assertEquals(response.jsonPath().getString("model"), "vios"); 	// Assert the product Model

		//	Create product object changing Name and Model
		ProductInputDTO updatedPro =  new ProductDTOBuilder().setName("Toyota Axio").setDescription("Car").setImage("https://newcar.carlist.my/uploads/model_year_colour_images/127_large.jpg").setManufacturer("Toyota")
									.setModel("Axio").setPrice(30).setShipping(0).setType("saloon").setUpc("Test").setUrl("https://en.wikipedia.org/wiki/Toyota_Vios").build();
		// Update the product 
		Response responseU = given().contentType("application/json").accept(ContentType.JSON)
							 		.body(updatedPro)
							 		.when().put("/products/"+productID); 
		Assert.assertEquals(responseU.getStatusCode(), HttpStatus.SC_OK); // Assert the status cod
		
		// Assert the updated details
		int productIdUpdated = responseU.jsonPath().getInt("id");	// Get the product ID after update
		Assert.assertEquals(responseU.jsonPath().getString("name"), "Toyota Axio"); 	// Assert the product name after update
		Assert.assertEquals(responseU.jsonPath().getString("model"), "Axio"); 	// Assert the product Model after update
		Assert.assertEquals(productID, productIdUpdated); 	// Assert that Product Id is not changing after update 
	}
	
	/***************************** Test cases for  Get All *********************************************/

	@Test // Verify that service allows to get products using get All
	public void verifyServiceAllowsToGetProducts ()
	{
		Response response = given().contentType("application/json").accept(ContentType.JSON)
							 		.when().get("/products"); 
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK); // Assert the status code
		Assert.assertNotNull(response.jsonPath().getInt("total"));	// Assert that total has values
		Assert.assertEquals(response.jsonPath().getInt("limit"), 10);	// Assert the default limit value
		Assert.assertEquals(response.jsonPath().getInt("skip"), 0);	// Assert the default skip value
		Assert.assertNotNull(response.jsonPath().getInt("data[0].id"));	// Assert that the Product Id has a value 
	}
	
	@Test	// Verify that Products load according to given limit
	public void verifyProoductsLoadAccordingToLimit ()
	{
		Response response = given().contentType("application/json").accept(ContentType.JSON)
		 				 		.when().get("/products"+"?$limit=5&$skip=2"); 
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK); // Assert the status code
		Assert.assertNotNull(response.jsonPath().getInt("total"));	// Assert that total has values
		Assert.assertEquals(response.jsonPath().getInt("limit"), 5);	// Assert the default limit value is 5
		Assert.assertEquals(response.jsonPath().getInt("skip"), 2);	// Assert the default skip value
		Assert.assertNotNull(response.jsonPath().getInt("data[4].id"));	// Assert that the Product Id has a value 
		
	}
	
	@Test	// Verify that Products skip loading according to given number
	public void verifyProductLoadingSkippingAccordingToGivenNumber ()
	{
		Response response = given().contentType("application/json").accept(ContentType.JSON)
		 				.when().get("/products"+"?$limit=5&$skip=100000000"); 
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK); // Assert the status code
		Assert.assertNotNull(response.jsonPath().getInt("total"));	// Assert that total has values
		Assert.assertEquals(response.jsonPath().getInt("limit"), 5);	// Assert the default limit value is 5
		Assert.assertEquals(response.jsonPath().getInt("skip"), 100000000);	// Assert the default skip value
	}
	
	
	
}

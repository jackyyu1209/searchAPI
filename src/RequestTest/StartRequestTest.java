/**********************************
Created on Dec 1, 2016

@author: Jacky Yu
==================================
Reference: "iTune Search API: 
https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api/#understand"
**********************************/
package RequestTest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import OtherObj.JsonReader;

public class StartRequestTest {
	
	  public JsonReader jr;
	  public JSONObject json;
	  public final String searhCount = "25";
	  public final String artistNameinURL = "jack+johnson";
	  public final String artistNameinJason="Jack Johnson";
	  public final String url = "https://itunes.apple.com/search?term="+artistNameinURL+"&limit="+searhCount;
	  
	  //Get Json after requesting.
	  @BeforeClass
	  public void beforeClass(){
		  jr = new JsonReader();
		  try {
				json = jr.readJsonFromUrl("https://itunes.apple.com/search?term=jack+johnson&limit=25");
			} catch (IOException | JSONException e) {
				Assert.fail("Can't get JSON of HTTP reponse",e);
			}
		  System.out.println("Json: "+json.toString());
	  }
  
	  //Test case 1: Check Http response status
	  @Test
	  public void GetHttpStatus() {
		  URL url;
		try {
			url = new URL("http://example.com");
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			int code = connection.getResponseCode();
			System.out.println("Http response statuse: "+code);
			Assert.assertEquals(code, 200);
		} catch (IOException e) {
			Assert.fail("Can't get JSON of HTTP reponse",e);
		}
	  }
	  
	  //Test case 2: Validate result count is matched to search count
	  @Test
	  public void Validate_resultCount() {
		  try {
			  Assert.assertEquals(json.get("resultCount"), 25);
		  } catch (JSONException e) {
			  Assert.fail("JSONException was thrown",e);
		  }
	  }
	  
	  //Test case 3: Validate artistName of all result
	  @Test
	  public void Validate_result_artistName() {
		  try {
			  JSONArray arr = json.getJSONArray("results");
			  //Check result count is matched to search count again
			  Assert.assertEquals(arr.length(), Integer.parseInt(searhCount));
			  for (int i = 0; i < arr.length(); i++)
			  {
			      String NameinJson = arr.getJSONObject(i).getString("artistName");
			      if (!NameinJson.equals(artistNameinJason)) {
			    	  System.out.println("Artist name doesn't match in:"+i+" ; result = "+NameinJson);
			    	  Assert.assertEquals(NameinJson, "Jack Johnson");
			      }
			  }
		  } catch (JSONException e) {
			  Assert.fail("JSONException was thrown",e);
		  }
	  }
}

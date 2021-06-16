#### Integration between WebPageTest and Slack

#### Steps to follow in integrating WPT & Slack

##### 1. Setting up a java project.
  * Clone this repository.
  * Update bot_token and channel_id in application.properties file (bottoken="", channelid="")

  We have currently two APIs which helps us in fetching and posting the test results in desired slack channel
 * /submittest: On hitting this URI, it will submit the test on "https://www.webpagetest.org/runtest.php?"
 * /testresult: Internally this API is used to fetch test results and ping back once result is ready.

  Note: We are using ngrok to setup a public URL against your localhost(e.g.8080) which is then used as a pingback URL followed by /testresult(as mentioned in controller class).
  'https://ngrokUrl/testresult'

##### 2. Making locally running server publicly accessible
 * Install and start ngrok
 * After installing ngrok run "ngrok http 8080"
 * This returns public URL which forwards request to localhost(e.g. 8080), on which our server is running.
 * Now our server is publicly accessible by the URLs returned by ngrok.

##### 3. Run your Test through Postman or by any other technique
 * Try hitting '/submittest' from your local host e.g. http://localhost:8080/submittest
 * Pass "URL to be tested" and "secret_API_KEY", "location" in request Body under "raw", make sure to select content type "JSON"/"XML"
 * Request Body example: 
  {
     "url":"https://www.youtube.com/",
     "key":"seceret_API_key"
     "location": "Dulles:Chrome.4G"
  }

 * After hitting the above URI, you should get "statusCode: 200" and once the response is generated in pingback, test results will be posted in slack channel. See below snippet

 ![image](https://user-images.githubusercontent.com/81590480/119186416-b553e600-ba95-11eb-8945-c75ff819c8c8.png)


##### 4. Create new slack app>> From Scratch

 ![image](https://user-images.githubusercontent.com/81590480/119186520-d288b480-ba95-11eb-9a7b-ff0e91db5968.png)


 * OK, you’ve created it! Now what? Well, we’re going to want to install the Slack App into your workspace and to install this in your workspace we have to add at least one feature or permission scope for the app. Click on the “permission scope” link there (alternatively, you can click on OAuth & Permissions in the sidebar. You’ll see Scopes on your page next.

 * Add any Scope: e.g. you can give chat:write, nce chat:write is added, scroll up. You should see a button where you can Install App to Workspace . If you don’t for some reason, you can navigate to Install Apps on the left sidebar.

 ![image](https://user-images.githubusercontent.com/81590480/119186606-f3e9a080-ba95-11eb-82da-1bd9cd3c0613.png)

 * After Re-Installing the app to your workspace, you can see OAuth tokens are added under user and bot token: Please take user_OAuth_token and paste it under application.properties file

 ![image](https://user-images.githubusercontent.com/81590480/119186695-12e83280-ba96-11eb-8f65-6ca7adf5d8ef.png)

 * Once all these things are done, run your application and hit /submittest API from postman or any testing tool. You will get your desired results in your slack channel once pingback url has successfully captured test results  

 ![image](https://user-images.githubusercontent.com/81590480/119186724-1b406d80-ba96-11eb-85cd-259e67afa283.png)

1. Open "https://console.cloud.google.com"
2. Go to API&Services, then click "Credentials".
3. Click "+ Create Credentials", select "OAuth Client ID"
4. Select "* Type Of Application" (Fo this project, select Web Application )
5. Add "http://localhost:8080/login/oauth2/code/google" in the Authorized redirect URIs and Click create.
6. Extract the Client-ID & Client-Secret and paste in the application.yaml file in the spring boot backend

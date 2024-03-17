<h1>Sunset and Sunrise</h1>
<hr>
<br>
<p>This project is a light web/REST service that gives you right time of a sunrise or a sunset. The service will send GET request to the <a href="#">https://api.sunrise-sunset.org/json</a> URL and then it will take sunrise and sunset times from .json file that we'll get as a response from API server.</p>
<hr>
<p>To see the results of this project you have to open browser or postman and put draft URL in there. After that it is important to put some values after '=' symbols to set variables.</p>
<h3>Draft URL:</h3>
<a href="#">https://api.sunrise-sunset.org/sunInfo?lat= &lng= &date= </a>
<h2>Updates:<h2>
<hr>
<p>Now it is possible to create country for your coordinates and save them in there, to get response for all this coordinates at the same time.
<br><hr>
To control this process you will need next drafts:</p>
<br>
<li>1. To create a country and save coordinates in there:</li>
<a href="#">http://localhost:8080/sunInfo/country/{countryName}/coordinates?lat={latValue}&lng={lngValue}</a>
<li>2. To get list of all safed countries and their coordinates:</li>
<a href="#">http://localhost:8080/allCountriesInfo</a>
<li>3. To get sunsets and sunrises for all this coordinates in one country:</li>
<a href="#">http://localhost:8080/sunInfo/country/{countryName}?date={dateValue}</a>
<li>4. To change name of a country:</li>
<a href="#">http://localhost:8080/country/{oldCountryName}/{newCountryName}</a>
<li>5. To change coordinates by id:</li>
<a href="#">http://localhost:8080/coordinates/{coordinatesId}?lat={latValue}&lng={lngValue}</a>
<li>6. To delete country:</li>
<a href="#">http://localhost:8080/sunInfo/country/{countryName}</a>
<li>7. To delete coordinates:</li>
<a href="#">http://localhost:8080/sunInfo/country/{countryName}/coordinates/{coordinatesId}</a>
<br><br>
<hr>
<p>Also it is possible to see history of your unnamed requests on URL "<a href="#">http://localhost:8080/sunInfo?lat={latValue}&lng={lngValue}&date={dateValue}</a>".
<hr> 
<p>To use this new ability, you will also need some drafts:</p>
<br>
<li>1. To get history (by date or by coordinates):</li>
<a href="#">http://localhost:8080/historyByDate</a>
<a href="#">http://localhost:8080/historyByCoordinates</a>
<li>2. To change date value:</li>
<a href="#">http://localhost:8080/date/{dateId}/{newDateValue}</a>
<li>3. To change coordinates by id:</li>
<a href="#">http://localhost:8080/coordinates/{coordinatesId}?lat={latValue}&lng={lngValue}</a>
<li>4. To delete date:</li>
<a href="#">http://localhost:8080/history/coordinates/{coordinates Id}</a>
<li>5. To delete coordinates:</li>
<a href="#">http://localhost:8080/history/date/{dateId}</a>

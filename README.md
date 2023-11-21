# media-app-backend

<h2>Topics covered</h2>
<ul>
  <li>Annotations</li>
  <li>Rest Controllers</li>
  <li>Error handling</li>
  <li>Dependency Management</li>
  <li>Spring Data JPA</li>
  <li>Entity lifecycle</li>
  <li>JDBC</li>
  <li>Spring security configuration</li>
  <li>Authentication using JWT tokens</li>
  <li>Testing</li>
</ul>


<h2>API DOCUMENTATION</h2>
<h3>Public API</h3>
<li>POST /login</li>
<blockquote>
  Request:
  {
    "email": "test@email.com",
    "password": "test123"
  }
</blockquote>
<blockquote>
  Response:
  {
      "accessToken": "token",
      "tokenType": "BEARER"
  }
</blockquote>

<li>POST /register</li>
<blockquote>
  Request:
  {
    "email": "test@email.com",
    "password": "test123"
    "firstName": "First",
    "lastName": "Last"
  }
</blockquote>
<blockquote>
  Response:
  {
      "accessToken": "token",
      "tokenType": "BEARER"
  }
</blockquote>


<h3>Secured API</h3>
<li>GET /movies/searchByTitle/{string}</li>
Search for movies by given string
<blockquote>
  Response:
  {
      "Title": "Hello",
      "Year": "2008",
      "Rated": "N/A",
      "Released": "10 Oct 2008",
      "Runtime": "129 min",
      "Genre": "Drama, Romance",
      "Director": "Atul Agnihotri",
      "Writer": "Atul Agnihotri, Chetan Bhagat, Jalees Sherwani",
      "Actors": "Sharman Joshi, Amrita Arora, Sohail Khan",
      "Plot": "Call-center workers receive a phone call from God.",
      "Language": "Hindi",
      "Country": "India",
      "Awards": "1 nomination",
      "Poster": "https://m.media-amazon.com/images/M/MV5BZGM5NjliODgtODVlOS00OWZmLWIzYzMtMTI2OWIzMTM1ZGRhXkEyXkFqcGdeQXVyNDUzOTQ5MjY@._V1_SX300.jpg",
      "Ratings": [
          {
              "Source": "Internet Movie Database",
              "Value": "3.3/10"
          }
      ],
      "Metascore": "N/A",
      "imdbRating": "3.3",
      "imdbVotes": "2,255",
      "imdbID": "tt1087856",
      "Type": "movie",
      "DVD": "05 Apr 2018",
      "BoxOffice": "N/A",
      "Production": "N/A",
      "Website": "N/A",
      "Response": "True"
  }
</blockquote>

<li>GET /movies/searchById/{imdbId}</li>
Get specific movie data
<blockquote>
  Response:
  {
      "Title": "Hello",
      "Year": "2008",
      "Rated": "N/A",
      "Released": "10 Oct 2008",
      "Runtime": "129 min",
      "Genre": "Drama, Romance",
      "Director": "Atul Agnihotri",
      "Writer": "Atul Agnihotri, Chetan Bhagat, Jalees Sherwani",
      "Actors": "Sharman Joshi, Amrita Arora, Sohail Khan",
      "Plot": "Call-center workers receive a phone call from God.",
      "Language": "Hindi",
      "Country": "India",
      "Awards": "1 nomination",
      "Poster": "https://m.media-amazon.com/images/M/MV5BZGM5NjliODgtODVlOS00OWZmLWIzYzMtMTI2OWIzMTM1ZGRhXkEyXkFqcGdeQXVyNDUzOTQ5MjY@._V1_SX300.jpg",
      "Ratings": [
          {
              "Source": "Internet Movie Database",
              "Value": "3.3/10"
          }
      ],
      "Metascore": "N/A",
      "imdbRating": "3.3",
      "imdbVotes": "2,255",
      "imdbID": "tt1087856",
      "Type": "movie",
      "DVD": "05 Apr 2018",
      "BoxOffice": "N/A",
      "Production": "N/A",
      "Website": "N/A",
      "Response": "True"
  }
</blockquote>

<li>GET /userMovie/{userMovieId}</li>
Get a specific user’s movie data
<blockquote>
  Response:
  {
    "id": 1,
    "movie": {
        "imdbId": "tt224775",
        "title": "Fal",
        "description": "The son of a dead arms dealer takes shooting lessons from a Congolese ex-child soldier. The weapon of choice: the FAL. A story about mutual fascination, tragic loss and automatic assault rifles.",
        "year": "2007",
        "runtime": "13 min",
        "rated": "N/A",
        "poster": "https://m.media-amazon.com/images/M/MV5BMjNlMzAxNmQtOGEwZi00NTEyLWI0NWYtMTlhNmE2YTA3ZDVhXkEyXkFqcGdeQXVyNTE1NjY5Mg@@._V1_SX300.jpg"
    },
    "watched": null,
    "priority": "LOW",
    "review": {
        "id": 3,
        "value": 20.0,
        "notes": "f sdf",
        "timestamp": "2023-11-15T16:05:45"
    }
}
</blockquote>

<li>POST /userMovie/addToWatch</li>
Add movie to watch list
<blockquote>
  Request:
  {
    "title": "Fal",
    "year": "2007",
    "rated": "N/A",
    "runtime": "13 min",
    "description": "The son of a dead arms dealer takes shooting lessons from a Congolese ex-child soldier. The weapon of choice: the FAL. A story about mutual fascination, tragic loss and automatic assault rifles.",
    "imdbId": "tt23",
    "poster": "https://m.media-amazon.com/images/M/MV5BMjNlMzAxNmQtOGEwZi00NTEyLWI0NWYtMTlhNmE2YTA3ZDVhXkEyXkFqcGdeQXVyNTE1NjY5Mg@@._V1_SX300.jpg",
    "priority": "LOW"
  }
</blockquote>
<blockquote>
  Response:
  {
    "id": 5,
    "movie": {
        "imdbId": "222222",
        "title": "Fal",
        "description": "The son of a dead arms dealer takes shooting lessons from a Congolese ex-child soldier. The weapon of choice: the FAL. A story about mutual fascination, tragic loss and automatic assault rifles.",
        "year": "2007",
        "runtime": "13 min",
        "rated": "N/A",
        "poster": "https://m.media-amazon.com/images/M/MV5BMjNlMzAxNmQtOGEwZi00NTEyLWI0NWYtMTlhNmE2YTA3ZDVhXkEyXkFqcGdeQXVyNTE1NjY5Mg@@._V1_SX300.jpg"
    },
    "watched": null,
    "priority": "LOW",
    "review": null
  }
</blockquote>

<li>POST /userMovie/addWatched</li>
Add movie to watched list
<blockquote>
  Request:
  {
    "title": "Fal",
    "year": "2007",
    "rated": "N/A",
    "runtime": "13 min",
    "description": "The son of a dead arms dealer takes shooting lessons from a Congolese ex-child soldier. The weapon of choice: the FAL. A story about mutual fascination, tragic loss and automatic assault rifles.",
    "imdbId": "tt23",
    "poster": "https://m.media-amazon.com/images/M/MV5BMjNlMzAxNmQtOGEwZi00NTEyLWI0NWYtMTlhNmE2YTA3ZDVhXkEyXkFqcGdeQXVyNTE1NjY5Mg@@._V1_SX300.jpg",
    "priority": "LOW"
  }
</blockquote>
<blockquote>
  Response:
  {
    "id": 5,
    "movie": {
        "imdbId": "222222",
        "title": "Fal",
        "description": "The son of a dead arms dealer takes shooting lessons from a Congolese ex-child soldier. The weapon of choice: the FAL. A story about mutual fascination, tragic loss and automatic assault rifles.",
        "year": "2007",
        "runtime": "13 min",
        "rated": "N/A",
        "poster": "https://m.media-amazon.com/images/M/MV5BMjNlMzAxNmQtOGEwZi00NTEyLWI0NWYtMTlhNmE2YTA3ZDVhXkEyXkFqcGdeQXVyNTE1NjY5Mg@@._V1_SX300.jpg"
    },
    "watched": "2023-11-21T17:12:09.874926",
    "priority": "LOW",
    "review": null
  }
</blockquote>

<li>GET /userMovie/getToWatch</li>
Get to watch movies
<blockquote>
  Response:
  [
    {
        "id": 5,
        "movie": {
            "imdbId": "222222",
            "title": "Fal",
            "description": "The son of a dead arms dealer takes shooting lessons from a Congolese ex-child soldier. The weapon of choice: the FAL. A story about mutual fascination, tragic loss and automatic assault rifles.",
            "year": "2007",
            "runtime": "13 min",
            "rated": "N/A",
            "poster": "https://m.media-amazon.com/images/M/MV5BMjNlMzAxNmQtOGEwZi00NTEyLWI0NWYtMTlhNmE2YTA3ZDVhXkEyXkFqcGdeQXVyNTE1NjY5Mg@@._V1_SX300.jpg"
        },
        "watched": null,
        "priority": "LOW",
        "review": null
    }
  ]
</blockquote>
  
<li>GET /userMovie/getWatched</li>
Get watched movies
<blockquote>
  Response: 
  {
    "id": 5,
    "movie": {
        "imdbId": "222222",
        "title": "Fal",
        "description": "The son of a dead arms dealer takes shooting lessons from a Congolese ex-child soldier. The weapon of choice: the FAL. A story about mutual fascination, tragic loss and automatic assault rifles.",
        "year": "2007",
        "runtime": "13 min",
        "rated": "N/A",
        "poster": "https://m.media-amazon.com/images/M/MV5BMjNlMzAxNmQtOGEwZi00NTEyLWI0NWYtMTlhNmE2YTA3ZDVhXkEyXkFqcGdeQXVyNTE1NjY5Mg@@._V1_SX300.jpg"
    },
    "watched": "2023-11-21T17:13:16.849781",
    "priority": "LOW",
    "review": null
  }
</blockquote>

<li>DELETE /userMovie/removeFromToWatch/{id}</li>
Delete a to watch movie

<li>GET review/get/{userMovieId}</li>
Get review from user’s movie
<blockquote>
  Response:
  {
    "id": 5,
    "value": 0.3,
    "notes": "BAD",
    "timestamp": "2023-11-21T16:40:49"
  }
</blockquote>
      
- POST Review/add/{userMovieId}
    - Add review to user’s movie
<blockquote>
  Request:
  {
    "value": 0.3,
    "notes": "BAD",
  }
</blockquote>
<blockquote>
  Response:
  {
    "id": 5,
    "value": 0.3,
    "notes": "BAD",
    "timestamp": "2023-11-21T16:40:49"
  }
</blockquote>
    
<li>PUT review/update/{userMovieId}</li>
Update review
<blockquote>
  Request:
  {
    "value": 10,
    "notes": "GOOD",
  }
</blockquote>
<blockquote>
  Response:
  {
    "id": 5,
    "value": 10,
    "notes": "GOOD",
    "timestamp": "2023-11-21T16:40:49"
  }
</blockquote>
      
<li>DELETE review/delete/{userMovieId}</li>
Delete review

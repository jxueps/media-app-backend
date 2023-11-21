# media-app-backend

<h2>Topics covered</h2>
- Annotations
- Rest Controllers
- Error handling
- Dependency Management
- Spring Data JPA
- Entity lifecycle
- JDBC
- Spring security configuration
- Authentication using JWT tokens
- Testing


<h2>API DOCUMENTATION</h2>
<h3>Public API</h3>
- POST /login
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

- POST /register

- <blockquote>
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
- GET /movies/searchByTitle/{string}
    - Search for movies by given string
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

- GET /movies/searchById/{imdbId}
    - Get specific movie data
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

- GET /userMovie/{userMovieId}
    - Get a specific user’s movie data
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

- POST /userMovie/addToWatch
    - Add movie to watch list
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

- POST /userMovie/addWatched
    - Add movie to watched list
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

- GET /userMovie/getToWatch
    - Get to watch movies
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
  
- GET /userMovie/getWatched
    - Get watched movies
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

      
- DELETE /userMovie/removeFromToWatch/{id}
    - Delete a to watch movie
 
- GET review/get/{userMovieId}
    - Get review from user’s movie
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
    
- PUT review/update/{userMovieId}
    - Update review
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
      
- DELETE review/delete/{userMovieId}
    - Delete review


# VectorDB

Open-source vector database designed for simplicity and speed, with flexible deployment options.

# Installation

## Binary Releases

*None*

## Build from Source

### Requirements

- Java 17 or later (tested using `17.0.9-zulu`)
- Git client
- Maven
- Docker

### MacOS/Linux/Unix

Clone this repository:

```shell
git clone https://github.com/tutikka/VectorDB.git
```

Change to cloned folder:

```shell
cd VectorDB
```

Clean, compile and package using `Maven`:

```shell
mvnw package
```

Change to created target directory:

```shell
cd target
```

Start the application:

```shell
java -jar vectordb-0.0.1-SNAPSHOT.jar
```

Or build a Docker image and run it, for example:

```shell
docker build -t vectordb/vectordb .
docker run -p 8080:8080 vectordb/vectordb
```

# Configuration

The application will look for a configuration file named `vectordb.properties` in the root directory of the application during startup. If the file is not found, default values (shown in the example below) will be used.

```properties
#
# directory for data files (default = 'data')
#
data.directory = data

#
# maximum number of vectors per index (default = 65536)
#
data.max_vectors_per_index = 65536
```

# Examples

## Random Values with Euclid Similarity

1. Create index with `3 dimensions` and `euclid similarity`
2. Create entries into the index with random values as embeddings
3. Search for the best matching entry based on given embedding
4. Clean up and delete index

[View full source (Python)](examples/random_example.py)

## Planet Positions with Euclid Similarity

This example maps the positions of the planets in our solar system on 1.1.2025 to a 3D space using the sun as the origin, and then tests which planets are closest.

1. Create index with `3` dimensions (X, Y and Z coordinates) and `euclid` similarity
2. Create entries to the index for each planet based on the position at 1.1.2025
3. Search for the 3 closest planets to the sun
4. Clean up and delete index

[View full source (Python)](examples/planets_example.py)

## RAG Example with OpenAI Embeddings and Chat Completion

This example is closer to a real-world scenario, where we have documents that we want to index to perform queries based on similarity, and then summarize best results based on a user's question.

1. Create index with `1536` dimensions (from OpenAI `ada-002` text embedding model) and `cosine` similarity
2. Create entries into the index by embedding each document using the OpenAI `ada-002` text embedding model
3. Search for the best matching entry based on the user's question (embedded with the same model)
4. Retrieve the original document identifier from the search results
5. Use a chat completion model (OpenAI `gpt-5`) to summarize the retrieved document based on the user's original question

*Note!* Make sure to add a `.env` file in the same directory with your **OpenAI API Key**

[View full source (Python)](examples/openai_example.py)

# API

## Summary

| Method | URI                         | Description                        |
|--------|-----------------------------|------------------------------------|
| `POST` | `/api/indexes`              | Create new index                   |
| `GET`  | `/api/indexes`              | List indexes                       |
| `GET`  | `/api/indexes/{id}`         | Get index                          |
| `POST` | `/api/indexes/{id}/entries` | Create new entry into index        |
| `GET`  | `/api/indexes/{id}/entries` | List entries in index              |
|`POST`| `/api/indexes/{id}/search`  | Submit search for entries in index |

## Reference

### Create New Index

**Method**

`POST`

**URI**

`/api/indexes`

**Query Parameters**

*None*

**Request Body**

```json
{
  "name": "test",
  "dimensions": 1536,
  "similarity": "cosine",
  "optimization": "none"
}
```

**Response Status**

- `HTTP 200`: Ok
- `HTTP 400`: Error creating index due to client input 
- `HTTP 500`: Error creating index due to server error

**Response Body**

```json
{
  "id": 1,
  "name": "test",
  "dimensions": 1536,
  "similarity": "cosine",
  "optimization": "none"
}
```

*Note!* The server will populate the `id` field, which is used to refer to the index in other API methods.

### List Indexes

**Method**

`GET`

**URI**

`/api/indexes`

**Query Parameters**

*None*

**Request Body**

*None*

**Response Status**

- `HTTP 200`: Ok

**Response Body**

```json
[
    {
      "id": 1,
      "name": "test",
      "dimensions": 1536,
      "similarity": "cosine",
      "optimization": "none"
    }
]
```

### Get Index

**Method**

`GET`

**URI**

`/api/indexes/{id}`

**Query Parameters**

- `id`: The index identifier

**Request Body**

*None*

**Response Status**

- `HTTP 200`: Ok
- `HTTP 404`: Index not found

**Response Body**

```json
{
  "id": 1,
  "name": "test",
  "dimensions": 1536,
  "similarity": "cosine",
  "optimization": "none",    
  "extras": {
    "_max_vectors": 65536,
    "_num_vectors": 1,
    "_size_on_disk": 1310728
  }
}
```

### Create New Entry into Index

**Method**

`POST`

**URI**

`/api/indexes/{id}/entries`

**Query Parameters**

- `id`: The index identifier

**Request Body**

```json
{
    "id": 1,
    "embedding": [
        0.1,
        0.2,
        0.3
    ]
}
```

**Response Status**

- `HTTP 200`: Ok
- `HTTP 400`: Error creating entry due to client input
- `HTTP 404`: Index not found
- `HTTP 500`: Error creating entry due to server error

**Response Body**

```json
{
  "id": 1,
  "embedding": [
    0.2672612,
    0.5345224,
    0.8017837
  ]
}
```

### List Entries in Index

**Method**

`GET`

**URI**

`/api/indexes/{id}/entries`

**Query Parameters**

- `id`: The index identifier
- `offset`: The position in the index where to start retrieving entries 
- `limit`: Maximun number of entries to retrieve

**Request Body**

*None*

**Response Status**

- `HTTP 200`: Ok
- `HTTP 400`: Error listing entries due to client input
- `HTTP 404`: Index not found
- `HTTP 500`: Error listing entries due to server error

**Response Body**

```json
[
  {
    "id": 1,
    "embedding": [
      0.2672612,
      0.5345224,
      0.8017837
    ]
  },
  {
    "id": 2,
    "embedding": [
      0.37139064,
      0.557086,
      0.7427813
    ]
  },
  {
    "id": 3,
    "embedding": [
      0.4242641,
      0.56568545,
      0.70710677
    ]
  }
]
```

### Submit Search for Entries in Index

**Method**

`POST`

**URI**

`/api/indexes/{id}/search`

**Query Parameters**

- `id`: The index identifier

**Request Body**

```json
{
    "embedding": [
        0.1,
        0.2,
        0.3
    ],
    "top": 3
}
```

**Response Status**

- `HTTP 200`: Ok
- `HTTP 400`: Error searching entries due to client input
- `HTTP 404`: Index not found
- `HTTP 500`: Error searching entries due to server error

**Response Body**

```json
{
  "matches": [
    {
      "id": 1,
      "distance": 5.9604644775390625E-8
    },
    {
      "id": 2,
      "distance": 0.007416725158691406
    },
    {
      "id": 3,
      "distance": 0.017292380332946777
    }
  ],
  "duration": 0,
  "scanned": 3,
  "total": 3,
  "similarity": "cosine"
}
```

# Embedded Use

VectorDB can be embedded into another Java application as a library. Follow the instructions above to download a binary release or build the `JAR` file yourself, and add it as a dependency to your project.

**Note!** This section still under development.

Example:

```java
//
// initialize configuration
//
// values are read from:
//
// - 'vectordb.properties' file from application working directory
// - properties file location defined by 'configuration' environment variable
//
try {
    Configuration.getConfiguration();
} catch (Exception e) {
    // todo
}

//
// create index
//
Index index = new Index();
index.setName("test");
index.setDimensions(3);
index.setSimilarity("cosine");
index.setOptimization("none");
try {
    DBService.getService().createIndex(index);
} catch (Exception e) {
    // todo    
}

//
// create entry into index
//
Entry entry = new Entry();
entry.setId(1);
entry.setEmbedding(new float[]{0.1, 0.2, 0.3});
try {
    DBService.getService().createEntry(1, entry)); // index id = 1
} catch (Exception e) {
    // todo    
}
```
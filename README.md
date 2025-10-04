# VectorDB

Open-source vector database designed for simplicity and speed, with flexible deployment options.

# Installation

## Binary Releases

## Build from Source

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
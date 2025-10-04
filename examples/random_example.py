import requests
import random

entries = 1000
dimensions = 1536

def embedding(n):
    result = []
    for f in range(n):
        result.append(random.random())
    return result

print("creating index...")
r = requests.post("http://localhost:8080/api/indexes", json={"name": "random_testing", "dimensions": dimensions, "similarity": "euclid", "optimization": "none"})
index = r.json()["id"]

for id in range(entries):
    print("\rcreating entry " + str(id + 1) + "...", end="")
    r = requests.post("http://localhost:8080/api/indexes/" + str(index) + "/entries", json={"id": id + 1, "embedding": embedding(dimensions)})

print("\nsearching entries...")
r = requests.post("http://localhost:8080/api/indexes/" + str(index) + "/search", json={"top": 3, "embedding": embedding(dimensions)})
print(r.json())

print("deleting index...")
r = requests.delete("http://localhost:8080/api/indexes/" + str(index))
print(r.status_code)

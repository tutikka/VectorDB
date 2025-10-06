import requests
import random

planets = {
    1: {
        "name": "Mercury",
        "coordinates": [-57939233.182,  -24194106.538,   3337053.522]
        },
    2: {
        "name": "Venus",
        "coordinates": [ 67838532.538,   84098478.443,  -2759288.145]
    },
    3: {
        "name": "Earth",
        "coordinates": [-145602772.647,  -36449009.571, 3575.688]
    },
    4: {
        "name": "Mars",
        "coordinates": [-78061694.530,  228170973.929,   6696071.901]
    },
    5: {
        "name": "Jupiter",
        "coordinates": [158346930.729,  743231116.167,  -6633881.972]
    },
    6: {
        "name": "Saturn",
        "coordinates": [1414866380.228, -262014437.835, -51751981.637]
    },
    7: {
        "name": "Uranus",
        "coordinates": [1660001681.784, 2407046942.121, -12579964.695]
    },
    8: {
        "name": "Neptune",
        "coordinates": [4469234589.744,  -95523921.720, -101024721.146]
    }
}

print("creating index...")
r = requests.post("http://localhost:8080/api/indexes", json={"name": "planets_testing", "dimensions": 3, "similarity": "euclid", "optimization": "none"})
index = r.json()["id"]

for k,v in planets.items():
    print("creating entry for id " + v["name"] + "...")
    r = requests.post("http://localhost:8080/api/indexes/" + str(index) + "/entries", json={"id": k, "embedding": v["coordinates"]})

print("searching entries...")
r = requests.post("http://localhost:8080/api/indexes/" + str(index) + "/search", json={"top": 3, "embedding": [0, 0, 0]})
print("3 closest planets to the sun:")
for match in r.json()["matches"]:
    print(planets[match["id"]]["name"])

print("deleting index...")
r = requests.delete("http://localhost:8080/api/indexes/" + str(index))
print(r.status_code)

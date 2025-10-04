from openai import OpenAI
import requests
import json
import os
from dotenv import load_dotenv

# simulated documents
documents = {
    1: "The rise of serverless computing has changed how developers think about scaling applications. Instead of managing dedicated infrastructure, engineers now stitch together ephemeral functions that execute only when triggered, reducing costs and complexity.",
    2: "Elena stepped off the train just as the rain began to fall. She pulled her grandmother’s yellow umbrella from her bag, smiling at the memory of summers spent chasing storms across the countryside.",
    3: "The headphones deliver surprisingly deep bass for their size. Battery life easily lasts a full workday, though the ear cushions get warm after extended use. For the price, the sound quality is impressive.",
    4: "Photosynthesis converts light energy into chemical energy within chloroplasts. Through the Calvin cycle, plants fix carbon dioxide into glucose, fueling growth and forming the basis of most food chains."
}

# user's question
prompt = "Can you tell me about serverless computing?"

load_dotenv()

client = OpenAI(api_key=os.getenv("OPENAI_API_KEY"))

# function to return text embedding using OpenAI ada-002 model
def embedding(s):
    response = client.embeddings.create(
        model="text-embedding-ada-002",
        input=s,
        encoding_format="float"
    )
    return response.data[0].embedding

# create index in VectorDB
print("creating index...")
r = requests.post("http://localhost:8080/api/indexes", json={"id": 1, "name": "openai_testing", "dimensions": 1536, "similarity": "cosine", "optimization": "none"})
print(r.status_code)
index = r.json()["id"]

# create entries into index from document embeddings
for k,v in documents.items():
    print("creating entry to index for document " + str(k) + "...")
    r = requests.post("http://localhost:8080/api/indexes/" + str(index) + "/entries", json={"id": k, "embedding": embedding(v)})
    print(r.status_code)

# search for best match in index based on prompt
print("searching index...")
r = requests.post("http://localhost:8080/api/indexes/" + str(index) + "/search", json={"top": 1, "embedding": embedding(prompt)})
print(r.status_code)
print(r.json())

# use chat completion to answer question based on search results
print("composing answer based on retrieved documents...")
id = r.json()["matches"][0]["id"]
completion = client.chat.completions.create(
    model="gpt-5",
    messages=[
        {"role": "developer", "content": "You will be provided documents retrieved from a search index, and should answer a user's question based on that information only"},
        {"role": "developer", "content": "The retrieved documents: " + documents[id]},
        {"role": "user", "content": prompt}
    ]
)
print(completion.choices[0].message.content)

# delete index
print("deleting index...")
r = requests.delete("http://localhost:8080/api/indexes/" + str(index))
print(r.status_code)


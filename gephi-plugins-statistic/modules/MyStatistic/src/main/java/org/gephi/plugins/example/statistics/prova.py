import networkx as nx
from node2vec import Node2Vec
import socket

# FILES
EMBEDDING_FILENAME = './embeddings.emb'
EMBEDDING_MODEL_FILENAME = './embeddings.model'

#PYTHON CLIENT
HOST = "localhost"
PORT = 8080

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect((HOST, PORT))

sock.sendall("Hello\n".encode())
data = sock.recv(1024).decode()
print("1) " + data)
if data == "olleH":
    sock.sendall("Bye\n".encode())
    data = sock.recv(1024).decode()
    print("2)", data)

    sock.close()
    print("Socket closed")





# GRAPH NX
# graph = nx.fast_gnp_random_graph(n=100, p=0.5)
graph = nx.MultiGraph()
# if edges are directed use MultiDiGraph()
# nx.addnode
# nx.addedge
#graph.add_node

# Precompute probabilities and generate walks
node2vec = Node2Vec(graph, dimensions=2, walk_length=2, num_walks=2, workers=1)

# Embed
model = node2vec.fit(window=10, min_count=1,
                     batch_words=4)  # Any keywords acceptable by gensim.Word2Vec can be passed, `diemnsions` and `workers` are automatically passed (from the Node2Vec constructor)

# Look for most similar nodes
# print("most similar"+str(model.wv.most_similar('2')))  # Output node names are always strings

# Save embeddings for later use
model.wv.save_word2vec_format(EMBEDDING_FILENAME)

# Save model for later use
model.save(EMBEDDING_MODEL_FILENAME)

data_path = "..\\..\\modules\\MyStatistic\\src\\main\\java\\org\\gephi\\plugins\\example\\statistics\\data\\"

file = open(data_path + "test.txt", "w")
file.write("most similar" + str(model.wv.most_similar('2')))
file.close()

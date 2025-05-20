#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <climits>
#include <cstring>

const int MAX_V = 1000;

bool bfs(int residualGraph[MAX_V][MAX_V], int V, int source, int sink, int parent[]) {
    std::vector<bool> visited(V, false);
    std::queue<int> q;
    q.push(source);
    visited[source] = true;
    parent[source] = -1;

    while (!q.empty()) {
        int u = q.front();
        q.pop();

        for (int v = 0; v < V; v++) {
            if (!visited[v] && residualGraph[u][v] > 0) {
                if (v == sink) {
                    parent[v] = u;
                    return true;
                }
                q.push(v);
                parent[v] = u;
                visited[v] = true;
            }
        }
    }

    return false;
}

int edmondsKarp(int graph[MAX_V][MAX_V], int V, int source, int sink) {
    int residualGraph[MAX_V][MAX_V];
    for (int u = 0; u < V; u++) {
        for (int v = 0; v < V; v++) {
            residualGraph[u][v] = graph[u][v];
        }
    }

    int parent[MAX_V];
    int maxFlow = 0;

    while (bfs(residualGraph, V, source, sink, parent)) {
        int pathFlow = INT_MAX;

        for (int v = sink; v != source; v = parent[v]) {
            int u = parent[v];
            pathFlow = std::min(pathFlow, residualGraph[u][v]);
        }

        for (int v = sink; v != source; v = parent[v]) {
            int u = parent[v];
            residualGraph[u][v] -= pathFlow;
            residualGraph[v][u] += pathFlow;
        }

        maxFlow += pathFlow;
    }

    return maxFlow;
}

int main(int argc, char* argv[]) {
    if (argc != 3) {
        std::cerr << "Usage: " << argv[0] << " <input_file> <output_file>" << std::endl;
        return 1;
    }

    std::ifstream inputFile(argv[1]);
    std::ofstream outputFile(argv[2]);

    if (!inputFile.is_open() || !outputFile.is_open()) {
        std::cerr << "Error opening file." << std::endl;
        return 1;
    }

    int V, E;
    inputFile >> V >> E;

    int graph[MAX_V][MAX_V];
    std::memset(graph, 0, sizeof(graph));

    for (int i = 0; i < E; i++) {
        int u, v, capacity;
        inputFile >> u >> v >> capacity;
        graph[u][v] = capacity;
    }

    int source = 0;
    int sink = V - 1;
    int maxFlow = edmondsKarp(graph, V, source, sink);

    outputFile << maxFlow << std::endl;

    inputFile.close();
    outputFile.close();

    return 0;
}

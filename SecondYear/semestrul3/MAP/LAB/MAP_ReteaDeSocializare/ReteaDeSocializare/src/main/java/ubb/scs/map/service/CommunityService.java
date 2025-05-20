package ubb.scs.map.service;

import java.util.*;

public class CommunityService {
    private final NetworkService networkService;
    private Map<Long, List<Long>> adjList;

    private List<Long> longestPathNodes = new ArrayList<>();
    private int longestPathLength;

    public CommunityService(NetworkService networkService) {
        this.networkService = networkService;
        this.adjList = new HashMap<>();
        initializeAdjacencyList();
    }

    private void initializeAdjacencyList() {
        networkService.getAllPrietenii().forEach(prietenie -> {
            addEdge(prietenie.getIdUser1(), prietenie.getIdUser2());
        });
    }

    private void resetLongestPath() {
        longestPathNodes = new ArrayList<>();
        longestPathLength = 0;
    }

    public void addEdge(Long node1, Long node2) {
        adjList.computeIfAbsent(node1, k -> new ArrayList<>()).add(node2);
        adjList.computeIfAbsent(node2, k -> new ArrayList<>()).add(node1);
    }

    public void deleteEdge(Long node1, Long node2) {
        adjList.getOrDefault(node1, new ArrayList<>()).remove(node2);
        adjList.getOrDefault(node2, new ArrayList<>()).remove(node1);
    }

    private void DFS(Long nodeId, Set<Long> visited, List<Long> component) {
        visited.add(nodeId);
        component.add(nodeId);
        adjList.getOrDefault(nodeId, Collections.emptyList()).forEach(neighbor -> {
            if (!visited.contains(neighbor)) DFS(neighbor, visited, component);
        });
    }

    public int connectedCommunities() {
        resetLongestPath();
        Set<Long> visited = new HashSet<>();
        int count = 0;
        for (Long nodeId : adjList.keySet()) {
            if (!visited.contains(nodeId)) {
                List<Long> component = new ArrayList<>();
                DFS(nodeId, visited, component);
                count++;
            }
        }
        return count;
    }

    private Long[] bfsFarthestNode(Long start, Map<Long, Long> parentMap) {
        Map<Long, Boolean> visited = new HashMap<>();
        Map<Long, Integer> distance = new HashMap<>();
        Queue<Long> queue = new LinkedList<>();

        queue.add(start);
        visited.put(start, true);
        distance.put(start, 0);
        parentMap.put(start, null);

        Long farthestNode = start;

        while (!queue.isEmpty()) {
            Long node = queue.poll();

            for (Long neighbor : adjList.getOrDefault(node, new ArrayList<>())) {
                if (!visited.getOrDefault(neighbor, false)) {
                    visited.put(neighbor, true);
                    queue.add(neighbor);
                    distance.put(neighbor, distance.get(node) + 1);
                    parentMap.put(neighbor, node);

                    if (distance.get(neighbor) > distance.get(farthestNode)) {
                        farthestNode = neighbor;
                    }
                }
            }
        }

        return new Long[]{farthestNode, (long) distance.get(farthestNode)};
    }

    private void reconstructPath(Long start, Long end, Map<Long, Long> parentMap) {
        longestPathNodes = new ArrayList<>();
        Long current = end;
        while (current != null) {
            longestPathNodes.add(current);
            current = parentMap.get(current);
        }
        Collections.reverse(longestPathNodes);
    }

    public void findLongestPathInGraph() {
        resetLongestPath();
        Set<Long> visitedComponents = new HashSet<>();
        Set<Long> nodesInLongestPathCommunity = new HashSet<>();

        for (Long node : adjList.keySet()) {
            if (!visitedComponents.contains(node)) {
                Map<Long, Long> parentMap1 = new HashMap<>();
                Long[] firstBFS = bfsFarthestNode(node, parentMap1);

                Map<Long, Long> parentMap2 = new HashMap<>();
                Long[] secondBFS = bfsFarthestNode(firstBFS[0], parentMap2);

                if (secondBFS[1] > longestPathLength) {
                    longestPathLength = secondBFS[1].intValue();
                    reconstructPath(firstBFS[0], secondBFS[0], parentMap2);

                    nodesInLongestPathCommunity.clear();
                    for (Long nodeInPath : longestPathNodes) {
                        nodesInLongestPathCommunity.add(nodeInPath);
                        for (Long neighbor : adjList.getOrDefault(nodeInPath, new ArrayList<>())) {
                            nodesInLongestPathCommunity.add(neighbor);
                        }
                    }

                    Set<Long> allConnectedNodes = new HashSet<>();
                    for (Long nodeInPath : longestPathNodes) {
                        bfsToAddConnectedNodes(nodeInPath, allConnectedNodes);
                    }
                    nodesInLongestPathCommunity.addAll(allConnectedNodes);
                }

                markComponentAsVisited(node, visitedComponents);
            }
        }

        System.out.println("Comunitatea cea mai socială include nodurile: " + nodesInLongestPathCommunity);
    }

    private void bfsToAddConnectedNodes(Long start, Set<Long> allConnectedNodes) {
        Queue<Long> queue = new LinkedList<>();
        Set<Long> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Long node = queue.poll();
            allConnectedNodes.add(node);

            for (Long neighbor : adjList.getOrDefault(node, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    }

    private void markComponentAsVisited(Long start, Set<Long> visitedComponents) {
        Queue<Long> queue = new LinkedList<>();
        queue.add(start);
        visitedComponents.add(start);

        while (!queue.isEmpty()) {
            Long node = queue.poll();
            for (Long neighbor : adjList.getOrDefault(node, new ArrayList<>())) {
                if (!visitedComponents.contains(neighbor)) {
                    visitedComponents.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    }

    public int mostSocialCommunity() {
        findLongestPathInGraph();
        return longestPathLength;
    }
}

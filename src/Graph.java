import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Graph {
    private static Hashtable<Integer, ArrayList<AdjacentNode>> adjacencyList = new Hashtable<>();
    private static Hashtable<Integer, ArrayList<AdjacentNode>> adjacencyListMST = new Hashtable<>();
    private static int nodesNumber;
    private boolean[] v = null;
    private boolean[] visitedFiboheaps;

    private static boolean isAdjacent(int node1, int node2) {
        ArrayList<AdjacentNode> adjList = adjacencyList.get(node1);
        for (AdjacentNode adjNode : adjList) {
            if (adjNode.nodeNumber == node2)

                return true;
        }

        return false;
    }

    public void GraphRandomGenerated(int n, int density) throws NumberFormatException {
        adjacencyList.clear();
        nodesNumber = n;
        v = new boolean[nodesNumber];
        int node1, node2, edgewt;
        int edge_count = 0;
        Random rgen = new Random();
        int no_edges = Math.round(((nodesNumber) * (nodesNumber - 1) / 2) * density / 100);
        while (edge_count < no_edges) {
            edgewt = rgen.nextInt(999) + 1;
            node1 = rgen.nextInt(nodesNumber);
            node2 = rgen.nextInt(nodesNumber);

            if ((node1 != node2)) {
                AdjacentNode adjacentNode = new AdjacentNode(node1, node2,
                        edgewt);
                AdjacentNode adjacentNode2 = new AdjacentNode(node2, node1,
                        edgewt);
                if (adjacencyList.get(node1) == null) {
                    ArrayList<AdjacentNode> adj = new ArrayList<>();
                    adjacencyList.put(node1, adj);
                }
                if (adjacencyList.get(node2) == null) {
                    ArrayList<AdjacentNode> adj = new ArrayList<>();
                    adjacencyList.put(node2, adj);
                }
                if (!isAdjacent(node1, node2)) {
                    adjacencyList.get(node1).add(adjacentNode);
                    adjacencyList.get(node2).add(adjacentNode2);
                    edge_count++;
                }
            }
        }
    }


    public void GraphFileInput(String args, String args2)
            throws FileNotFoundException {
        Scanner sc = new Scanner(new File(args2));
        int nodesNumber = sc.nextInt();
        int edgeNumber = sc.nextInt();
        Graph.nodesNumber = nodesNumber;
        while (sc.hasNextInt()) {
            int node1 = sc.nextInt();
            int node2 = sc.nextInt();
            int edgeWeight = sc.nextInt();
            if ((node1 != node2)) {
                AdjacentNode adjacentNode = new AdjacentNode(node1, node2,
                        edgeWeight);
                AdjacentNode adjacentNode2 = new AdjacentNode(node2, node1,
                        edgeWeight);
                if (adjacencyList.get(node1) == null) {
                    ArrayList<AdjacentNode> adj = new ArrayList<>();
                    adjacencyList.put(node1, adj);
                }
                if (adjacencyList.get(node2) == null) {
                    ArrayList<AdjacentNode> adj = new ArrayList<>();
                    adjacencyList.put(node2, adj);
                }
                if (!isAdjacent(node1, node2)) {
                    adjacencyList.get(node1).add(adjacentNode);
                    adjacencyList.get(node2).add(adjacentNode2);
                }
            }
        }
    }
    public void PrimSimpleScheme(String args) {
        Hashtable<Integer, Integer> mst = new Hashtable<>();
        ArrayList<AdjacentNode> Node;
        ArrayList<String> output = new ArrayList<>();
        Hashtable<Integer, Boolean> visited = new Hashtable<>();

        adjacencyListMST.put(0, adjacencyList.get(0));
        visited.put(0, true);

        int mstCost = 0;
        int current = 0;

        while (true) {
            Set<Integer> keySet = adjacencyListMST.keySet();
            int min = Integer.MAX_VALUE;
            AdjacentNode nextTreeNode = null;
            for (Integer nodeId : keySet) {
                Node = adjacencyList.get(nodeId);
                for (AdjacentNode adjacentNode : Node) {
                    if (visited.get(adjacentNode.nodeNumber) == null) {
                        if (adjacentNode.weight < min) {
                            min = adjacentNode.weight;
                            nextTreeNode = adjacentNode;
                            current = nodeId;
                        }
                    }
                }
            }
            visited.put(nextTreeNode.nodeNumber, true);
            mstCost = mstCost + min;
            mst.put(nextTreeNode.nodeNumber, current);
            output.add(current + " " + nextTreeNode.nodeNumber);
            adjacencyListMST.put(nextTreeNode.nodeNumber,
                    adjacencyList.get(nextTreeNode.nodeNumber));
            if (adjacencyListMST.size() == nodesNumber)
                break;
        }
        if (!args.equals("-r")) {
            System.out.println(mstCost);
            for (String out : output) {
                System.out.println(out);
            }
        }
    }

    void runDFS(int seed) {
        v[seed] = true;
        ArrayList<AdjacentNode> adjacentNodes = adjacencyList.get(seed);
        for (AdjacentNode ad : adjacentNodes) {
            if (!v[ad.nodeNumber]) {
                runDFS(ad.nodeNumber);
            }
        }
    }

   boolean isConnected() {
        int i;
        for (i = 0; i < v.length; i++) {
            if (!v[i]) {
                System.out.println("the graph isn't connected");

                return false;
            }
        }

        return true;
    }

    private AdjacentNode getEdgeEnd(Node node1) {
        int minEdgeValue = Integer.MAX_VALUE;
        AdjacentNode minEdge = null;
        ArrayList<AdjacentNode> adjacentNodes = adjacencyList
                .get(node1.id_node);
        for (AdjacentNode iter : adjacentNodes) {
            if (!visitedFiboheaps[iter.nodeNumber]) {
                continue;
            }
            if (iter.weight < minEdgeValue) {
                minEdgeValue = iter.weight;
                minEdge = iter;
            }
        }

        return minEdge;
    }


    void PrimFHeap(String args) {
        Heap heap = new Heap();
        visitedFiboheaps = new boolean[nodesNumber];
        AdjacentNode edgeEnd;
        Node node;

        int noEdgesAdded = 0;
        long cost = 0;
        heap.insertNode(new Node(0), 0);
        adjacencyListMST.clear();

        while (noEdgesAdded != nodesNumber) {
            node = heap.removeMin();
            noEdgesAdded++;
            edgeEnd = getEdgeEnd(node);

            adjacencyListMST.put((node.id_node), new ArrayList<>());
            visitedFiboheaps[node.id_node] = true;
            if (edgeEnd != null) {
                AdjacentNode newNode = new AdjacentNode(edgeEnd.nodeNumber, node.id_node, edgeEnd.weight);
                adjacencyListMST.get(edgeEnd.nodeNumber).add(newNode);
                cost += edgeEnd.weight;
            }

            ArrayList<AdjacentNode> adjacentNodes = adjacencyList
                    .get(node.id_node);

            for (AdjacentNode adjacentNode : adjacentNodes) {
                if (visitedFiboheaps[adjacentNode.nodeNumber]) {
                    continue;
                }
                if (heap.isPresent(adjacentNode.nodeNumber)) {
                    heap.decreaseKey(heap.getNode(adjacentNode.nodeNumber), adjacentNode.weight);
                } else {
                    heap.insertNode(new Node(adjacentNode.nodeNumber), adjacentNode.weight);
                }
            }
        }

        if (!args.equals("-r")) {
            System.out.println(cost);
            Set<Integer> keySet1 = adjacencyListMST.keySet();
            for (Integer i : keySet1) {
                ArrayList<AdjacentNode> adjacentNodes = adjacencyListMST.get(i);
                for (AdjacentNode ad : adjacentNodes) {
                    System.out.println(i + " " + ad.nodeNumber);
                }
            }
        }
    }
}

class AdjacentNode {
    private int sourceNode;
    int nodeNumber;
    int weight;

    public AdjacentNode() {
        this.sourceNode = -1;
        this.nodeNumber = -1;
        this.weight = 0;
    }

    public AdjacentNode(int sourceNode, int nodeNumber, int edgeCost) {
        this.sourceNode = sourceNode;
        this.nodeNumber = nodeNumber;
        this.weight = edgeCost;
    }
}
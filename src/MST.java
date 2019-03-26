import java.io.IOException;

public class MST {
    public static void main(String[] args) throws NumberFormatException, IOException {
        Graph g = new Graph();

        if (args[0].equals("-r")) {
            do {
                g.GraphRandomGenerated(Integer.parseInt(args[1]),
                        Integer.parseInt(args[2]));
                g.runDFS(0);
            } while (!g.isConnected());
            long start = System.currentTimeMillis();
            g.PrimSimpleScheme(args[0]);
            long end = (System.currentTimeMillis()) - start;
            System.out.println("Prim Simple Scheme time: " + end);
            start = System.currentTimeMillis();
            g.PrimFHeap(args[0]);
            end = (System.currentTimeMillis()) - start;
            System.out.println("Prim Fheap Scheme time: " + end);
        }
        else {
            g.GraphFileInput(args[0], args[1]);
            if (args[0].equals("-s"))
                g.PrimSimpleScheme(args[0]);
            if (args[0].equals("-f"))
                g.PrimFHeap(args[0]);
        }
    }
}
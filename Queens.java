import java.util.ArrayList;

public class Queens {

    private static void printQueens() {
        sols.add(mainArray.clone());
    }

    private static int isConsistent(int n_) {
        for (int i=0; i<n_; i++) {
            if (mainArray[i]==mainArray[n_])
                return 0;
            if ((mainArray[i]-mainArray[n_])==(n_-i))
                return 0;
            if ((mainArray[n_]-mainArray[i])==(n_-i))
                return 0;
        }
        return 1;
    }

    private static void iterate() {
        mainArray = new int[n];
        iterateOver(0);
    }

    private static void iterateOver(int j) {
        if (j == n)
            printQueens();
        else {
            for (int i = 0; i < n; i++) {
                mainArray[j] = i;
                if (isConsistent(j) > 0)
                    iterateOver(j + 1);
            }
        }
    }

    public static ArrayList<int []> main(int N) {
        n = N;
        iterate();
        return sols;
    }

    public static ArrayList<int []> sols = new ArrayList<int []>();
    private static int n;
    private static int[] mainArray;
}

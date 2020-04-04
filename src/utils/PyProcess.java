package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// Python process class
public class PyProcess{
    private String interpreter, csv;
    private Process process;
    public PyProcess(String inter, String file){
        interpreter = inter;
        csv = file;
    }
    // Process running method
    private void run() throws IOException, ProcessException {
        process = Runtime.getRuntime().
                exec(interpreter + " " + "src/getdata.py" + " " + csv);
        try (BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            if (err.readLine() == "") {
                System.out.print(err.toString());
                throw new ProcessException(err);
            }
        }
    }
    // Data extracting method
    public DataFrameViewer getData() throws IOException, ProcessException {
        Object[] cols, inds;
        int w, h;
        String s;

        run();
        try (BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            w = Integer.parseInt(output.readLine()) + 1; //+1 for index column
            h = Integer.parseInt(output.readLine());
            inds = new Object[h];
            cols = new Object[w];
            for (int i = 1; i < w; i++)
                cols[i] = output.readLine();
            for (int i = 0; i < h; i++)
                inds[i] = output.readLine();
            String res[][] = new String[h][w - 1];
            for (int i = 0; i < h; i++)
                for (int j = 0; j < w - 1; j++)
                    res[i][j] = output.readLine();
            return new DataFrameViewer(res, cols, inds);
        }
    }
}

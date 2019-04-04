package br.com.lucolimac.barkscanner.util;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;

import java.util.concurrent.ExecutionException;

public class MatlabTest {
    MatlabEngine eng;

    {
        try {
            eng = MatlabEngine.startMatlab();
            double[] a = {2.0, 4.0, 6.0};
            double[] b = {2.0, 4.0, 6.0};
            double[] roots = eng.feval("sqrt", a);
            String j = eng.feval("corrcoef", a, b);
            for (double e : roots) {
                System.out.println(e);
            }
            eng.close();
        } catch (EngineException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

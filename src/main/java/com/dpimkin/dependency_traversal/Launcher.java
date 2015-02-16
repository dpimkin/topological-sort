package com.dpimkin.dependency_traversal;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Launcher {

    public static void printUsage() {
        System.out.println( "DependencyTraversal by Dmitriy Pimkin, 2014\nUsage: java dependency_traversal.jar [input-files]\n\nExample of input file is:\na: b, c\nd:\ne: f, a" );
    }

    public static void processFile( String fileName ) throws IOException {
        try ( BufferedReader br = new BufferedReader(new FileReader( fileName ))) {
            Map<String,Set<String>> map = new HashMap<>();
            for ( String line = br.readLine(); line != null; line = br.readLine() ) {
                Map.Entry<String,Set<String>> entry = Helper.distinguish( line );
                map.put(entry.getKey(), entry.getValue());
            }
            Runtime runtime = Runtime.getRuntime();
            long startTime = System.nanoTime();
            long memUsageStart = runtime.totalMemory() - runtime.freeMemory();
            List result = DependencyTraversal.traverse(map);
            long elapsedTime = System.nanoTime() - startTime;
            long memUsageEnd = runtime.totalMemory() - runtime.freeMemory();
            System.out.println( result );
            System.out.printf( "It takes us " + elapsedTime + " ms. Besides memory usage influence is " + (memUsageEnd - memUsageStart) + ".\n" );

        }
    }

    public static void main( String... args ) throws Exception {

        if ( args.length < 1 ) { // show usage on no param launch
            printUsage();
            return;
        }

        for ( String fileName : args ) {
            System.out.println( "Press [ENTER] to process " + fileName);
            System.in.read();
            processFile(fileName);
        }
    }
}

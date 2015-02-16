package com.dpimkin.dependency_traversal;


import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Helper {

    public static Set<String> parse( String dependencies ) {
        String[] parts = dependencies.split( "," );
        Set<String> result = new HashSet<>( parts.length );
        boolean atLeastOne = false;
        for ( String part : parts ) {
            String trimmed = part.trim();
            if ( ! "".equals( trimmed )) {
                result.add(trimmed);
                atLeastOne = true;
            }
        }
        return atLeastOne ? result : null;
    }

    public static Map.Entry<String, Set<String>> distinguish( String line ) {
        String[] parts = line.split( ":", 2 );
        if ( parts.length == 0 ) {
            throw new IllegalArgumentException( "bad syntax in following line: " + line );
        }
        return new AbstractMap.SimpleEntry<>( parts[0].trim(), parts.length == 2 ? parse( parts[ 1 ] ) : null );
    }


}

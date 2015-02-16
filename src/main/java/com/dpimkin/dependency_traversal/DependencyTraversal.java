package com.dpimkin.dependency_traversal;

import java.util.*;

public class DependencyTraversal {

    static boolean isNullOrEmpty( Collection coll ) {
        return coll == null || coll.isEmpty();
    }


    public static <Trait> List<Trait> traverse( Map<Trait,Set<Trait>> graph ) {

        Queue<Trait> sprint = new LinkedList<>();
        Map<Trait,Set<Trait>> unifiedGraph = new HashMap<>( graph );

        {
            Set<Trait> keys = graph.keySet();
            for ( Trait key : keys ) {
                Set<Trait> dependencies = graph.get( key );
                if ( isNullOrEmpty( dependencies )) {
                    sprint.add( key );
                } else {
                    for ( Trait dependency : dependencies ) {
                        if ( ! keys.contains( dependency )) {
                            unifiedGraph.put( dependency, null );
                            sprint.add( dependency );
                        }
                    }
                }
            }
        }

        int estimatedSolutionCapacity = unifiedGraph.size();
        List<Trait> solution = new ArrayList<>( estimatedSolutionCapacity );
        Set<Trait> recognized = unifiedGraph.keySet();
        Set<Trait> processed = new HashSet<>( estimatedSolutionCapacity );

        while ( !sprint.isEmpty() ) {
            Trait dependency = sprint.poll();
            if ( processed.add( dependency )) {
                solution.add( dependency );
            }

            for ( Trait it : recognized ) {
                Set<Trait> itsDependencies = unifiedGraph.get( it );
                if ( itsDependencies != null && !itsDependencies.isEmpty()) {
                    if ( ! processed.contains( it ) && processed.containsAll( itsDependencies )) {
                        sprint.add( it );
                    }
                }
            }
        }

        if ( solution.containsAll( recognized )) {
            return solution;
        }

        recognized.removeAll( solution );
        throw new DiscoveredLoopException( "Loop was found among following nodes: " + recognized.toString() );
    }


}

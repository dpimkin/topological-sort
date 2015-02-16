package com.dpimkin.dependency_traversal;

public class DiscoveredLoopException extends RuntimeException {

    public DiscoveredLoopException(String message) {
        super(message);
    }

    public DiscoveredLoopException() {
        super();
    }
}

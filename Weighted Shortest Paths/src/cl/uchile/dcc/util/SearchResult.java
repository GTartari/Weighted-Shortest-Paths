package cl.uchile.dcc.util;

import java.util.LinkedList;

import cl.uchile.dcc.model.Vertex;

public class SearchResult {
    private final LinkedList<Vertex> path;
    private final int visitedNodes;

    public SearchResult(LinkedList<Vertex> path, int visitedNodes) {
        this.path = path;
        this.visitedNodes = visitedNodes;
    }

    public LinkedList<Vertex> getPath() {
        return path;
    }

    public int getVisitedNodes() {
        return visitedNodes;
    }
}

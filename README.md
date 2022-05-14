# GraphIsomorphism

## Installation

- Install [Nauty](https://pallini.di.uniroma1.it/). Set the environment variable `NAUTY_HOME`.
- Install [GAP](https://www.gap-system.org/Manuals/doc/ref/chap0.html). Set the environment variable `GAP_HOME`.

- Compiled using maven 3.6.3 and JDK 17. JUnit 5 is a test dependency.

## Graph Format
Graphs can be read using `GraphParser.parseGraph`. Currently two formats are accepted:
- Adjacency list format: The first line should be two integers `n m` where n is the number of vertices and m is the number of edges. The next m lines should be two integers `u v` which are 0-indexed vertices of an edge.
- Adjacency matrix format: The first line should be a single integer `n` where n is the number of vertices. The next n lines should contain n 0's and 1's with spaces in between representing the adjacency matrix.

Graphs can also be generated using methods from `GraphGenerator`.

## Graph Isomorphism
Test isomorphism of graphs using `GraphIsomorphism.isIsomorphic`. Currently three methods of graph isomorphism are implemented.

- Nauty: Use nauty's suite of programs to determine isomorphism.
- Naive: Reduce to a single call of string isomorphism. The strings encode the edges of the graphs. The group is the induced action of S<sub>n</sub> acting on the family of 2-element subsets of [n].
- Degree: Implementation of Luks's original reduction from bounded degree graph isomorphism to string isomorphism.

Certificates are currently not implemented.
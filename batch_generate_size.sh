#!/bin/bash
# The purpose of this script is to terminate the generation of sudokus that take too long.

for i in {1..100}
do
   timeout 20s python3 sudokuGenerator_size.py 2 2 $i
done

for i in {1..100}
do
   timeout 20s python3 sudokuGenerator_size.py 2 3 $i
done

for i in {1..100}
do
   timeout 20s python3 sudokuGenerator_size.py 2 4 $i
done

for i in {1..100}
do
   timeout 20s python3 sudokuGenerator_size.py 3 3 $i
done

for i in {1..100}
do
   timeout 20s python3 sudokuGenerator_size.py 3 4 $i
done

for i in {1..100}
do
   timeout 20s python3 sudokuGenerator_size.py 3 5 $i
done

for i in {1..100}
do
   timeout 20s python3 sudokuGenerator_size.py 3 5 $i
done

for i in {1..100}
do
   timeout 20s python3 sudokuGenerator_size.py 4 4 $i
done

for i in {1..100}
do
   timeout 20s python3 sudokuGenerator_size.py 4 5 $i
done

for i in {1..100}
do
   timeout 20s python3 sudokuGenerator_size.py 5 5 $i
done
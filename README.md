# SUDOKUS: BACKTRACKING vs KNUTH

# Programas de resolución de Sudokus

## sudokuSolver.java

Este es el programa que usa el algoritmo de backtracking. Para usarlo, se le proporciona el path del sudoku a resolver, la dimensión X, la dimensión Y, y el path del archivo en el que escribir el resultado.
Ejemplo de ejecución:

```
$java sudokuSolver py-sudokus/Size/2x2/Sudoku1_2x2 2 2 results_backtracking_size
```

Este comando resolverá el sudoku de 2x2, y almacenará su nombre y tiempo de ejecución en results_backtracking_size.
En caso de querer establecer un tiempo máximo de ejecución, se añadiría en el inicio el comando ‘timeout Xs’ siendo x el tiempo máximo de ejecución en segundos.

## sudokuSolverX.py

Este programa emplea el Knuth’s Algorithm X obtenido de internet[1]. Para usarlo, se le proporciona el path del sudoku a resolver, la dimensión X, la dimensión Y, y el path del archivo en el que escribir el resultado.
Ejemplo de ejecución:

```
$python3 sudokuSolverX.py py-sudokus/Size/2x2/Sudoku1_2x2 2 2 results_knuth_size.csv
```

En caso de querer establecer un tiempo máximo de ejecución, se añadiría en el inicio el comando ‘timeout Xs’ siendo x el tiempo máximo de ejecución en segundos.

# Programas de Generación de Sudokus

Ambos programas emplean la librería py-sudoku[4] para generar los sudokus

## sudokuGenerator_difficulty.py

Para usarlo, se modifican los valores del bucle for en el script, para determinar el rango de dificultades a usar, así como la distancia entre ellos. El programa genera 100 sudokus de cada tipo.
Ejemplo de ejecución:

```
$python3 sudokuGenerator_difficulty.py
```

El programa se encarga de almacenar todo en carpetas.

## sudokuGenerator_size.py

Para usarlo, el programa recibe la dimensión X, la dimensión Y, y una seed. Con ello, genera un sudoku con esas características. Este programa genera los sudokus de 1 en 1 para permitir su combinación con el shell script batch_generate_size.sh el cual ejecuta este script en bucle con un timeout, para evitar que se quede atascado en un sudoku.
Ejemplo de ejecución:

```
$python3 sudokuGenerator_size.py 2 3 10
```

Este comando generará un sudoku de dimensión 2x3, con seed 10. A continuación lo guarda en su carpeta correspondiente.

# Programas de Ejecución de Pruebas

## execute\_\*.sh

Estos scripts se encargan de ejecutar ambos programas utilizando los distintos sudokus. Separando los resultados en diferentes archivos dependiendo del tipo de test.

## master_script.sh

Este script únicamente llama al resto de scripts de pruebas, en distintas terminales. Su única función es comodidad a la hora de repetir las pruebas.

# Formato de los Sudokus

El formato de los sudokus consiste en un archivo sin extensión que contiene todos los dígitos, separando los valores de columnas con espacios, y de filas con ‘\n’.
Si no se usa este formato, se producen errores de lectura.

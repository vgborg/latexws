# About
Small Java webservice rendering LaTeX formulas to SVG (PNG planned). 

Just a thin layer over jlatexmath.

Essential libraries used:
* [org.scilab jlatexmath](https://github.com/opencollab/jlatexmath) - main library rendering LaTeX (to Graphics2D)
* Apache Batik - creating SVG code
* SpringBoot - web/API layer

# Basic usage / Quick start
```
./mvnw spring-boot:run
```
Open demo page

http://localhost:8080/

Open API documentation under

http://localhost:8080/swagger-ui/index.html

Or just fetch example SVGs with webbrowser

* http://localhost:8080/api/latex/svg/?formula=e%20%3D%20m%20%5Ccdot%20c%20%5E%202
* http://localhost:8080/api/latex/svg/?formula=e%20%3D%20m%20%5Ccdot%20c%20%5E%202&size=200
* http://localhost:8080/api/latex/svg/?formula=e%20%3D%20lim%281%20%2B%201%2Fn%29%5En%5Cqquad%20fuer%5Cqquad%20n-%3E%5Cinfty
* http://localhost:8080/api/latex/svg/?formula=e%20%5E%7Bi%20%5Cpi%7D%20%2B%201%20%3D%200
* http://localhost:8080/api/latex/svg/?formula=e%20%5E%7Bi%20%5Cpi%7D%20%2B%201%20%3D%200&size=300
* http://localhost:8080/api/latex/svg/?formula=p%20%5Ccdot%20V%20%3D%20n%20%5Ccdot%20R%20%5Ccdot%20T

# Docker build
```
./mvnw clean package -DskipTests
docker build . -t latexws 
```

## Docker run
```
docker run --rm -p 8080:8080 latexws 
```


# Limitations
Some LaTeX-tags seem to be unsupported
* `\begin{equation}`
* `\begin{array}[b]{r}`
* `\color{...}`
* `\begin{align*}`

# TODO

* No security check present - to be implemented (or via reverse proxy)
* perhaps switch to more recent Java-version. Used JDK 8 because the docker image contained required AWT classes